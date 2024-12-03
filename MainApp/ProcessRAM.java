package MainApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ProcessRAM {

    // Method to parse a ROM file
    public void parseROM(File romFile) {
        if (romFile == null || !romFile.exists()) {
            System.out.println("Invalid ROM file.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(romFile)) {
            // Read the first 192 bytes of the ROM header
            byte[] header = new byte[192];
            int bytesRead = fis.read(header);
            if (bytesRead < header.length) {
                System.out.println("Incomplete ROM file.");
                return;
            }

            // Parse basic ROM information
            String gameTitle = new String(header, 0xA0, 12).trim(); // Game title (12 bytes at offset 0xA0)
            String gameCode = new String(header, 0xAC, 4).trim();   // Game code (4 bytes at offset 0xAC)
            String makerCode = new String(header, 0xB0, 2).trim();  // Maker code (2 bytes at offset 0xB0)

            // Print parsed information
            System.out.println("Game Title: " + gameTitle);
            System.out.println("Game Code: " + gameCode);
            System.out.println("Maker Code: " + makerCode);
            System.out.println("ROM Size: " + romFile.length() + " bytes");

        } catch (IOException e) {
            System.err.println("Failed to parse ROM: " + e.getMessage());
        }
    }
}
