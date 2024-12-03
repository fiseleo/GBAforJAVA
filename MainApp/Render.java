package MainApp;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Render {
    private long window;

    public void init() {
        // Initialize GLFW
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW");
        }

        // Create a window
        window = GLFW.glfwCreateWindow(240, 160, "GBA Emulator", 0, 0);
        if (window == 0) {
            throw new IllegalStateException("Failed to create GLFW window");
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    public void renderFrame(int[] frameBuffer) {
        // Clear screen
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

        // Draw frame buffer as pixels
        GL11.glDrawPixels(240, 160, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, frameBuffer);

        // Swap buffers
        GLFW.glfwSwapBuffers(window);
    }

    public boolean isRunning() {
        return !GLFW.glfwWindowShouldClose(window);
    }

    public void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
