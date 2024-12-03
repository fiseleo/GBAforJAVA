package MainApp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.io.File;

public class StartRAM {

    public static void startEmulator(File romFile) {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Create an OpenGL window
        long window = GLFW.glfwCreateWindow(240, 160, "GBA Emulator - " + romFile.getName(), 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create GLFW window");
        }

        // Set OpenGL context
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Load ROM data
        byte[] romData = loadROM(romFile);
        if (romData == null) {
            System.err.println("Failed to load ROM.");
            return;
        }

        // Initialize emulator components
        Memory memory = new Memory(romData);
        CPU cpu = new CPU(memory);
        PPU ppu = new PPU();

        // Main emulator loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Execute CPU cycle
            cpu.step();

            // Render frame using PPU
            ppu.renderFrame();

            // Draw frame buffer using OpenGL
            renderFrameBuffer(ppu.getFrameBuffer());

            // Swap buffers and poll events
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }

        // Cleanup
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    private static byte[] loadROM(File romFile) {
        try {
            return java.nio.file.Files.readAllBytes(romFile.toPath());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void renderFrameBuffer(int[] frameBuffer) {
        // Clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Render frame buffer
        GL11.glDrawPixels(
                240, 160,                   // GBA screen resolution
                GL11.GL_RGBA,               // Format (RGBA)
                GL11.GL_UNSIGNED_BYTE,      // Data type
                java.nio.IntBuffer.wrap(frameBuffer) // Frame buffer as IntBuffer
        );
    }
}
