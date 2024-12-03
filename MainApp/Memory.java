package MainApp;

public class Memory {
    private byte[] bios = new byte[0x4000];      // 16KB BIOS
    private byte[] wram = new byte[0x40000];    // 256KB WRAM
    private byte[] iram = new byte[0x8000];     // 32KB IRAM
    private byte[] rom;                         // Game ROM

    public Memory(byte[] romData) {
        this.rom = romData; // Load the ROM into memory
    }

    // Read a byte from memory
    public byte read8(int address) {
        if (address < 0x4000) {
            return bios[address];
        } else if (address >= 0x02000000 && address < 0x02040000) {
            return wram[address - 0x02000000];
        } else if (address >= 0x03000000 && address < 0x03008000) {
            return iram[address - 0x03000000];
        } else if (address >= 0x08000000) {
            return rom[address - 0x08000000];
        }
        return 0;
    }

    // Write a byte to memory
    public void write8(int address, byte value) {
        if (address >= 0x02000000 && address < 0x02040000) {
            wram[address - 0x02000000] = value;
        } else if (address >= 0x03000000 && address < 0x03008000) {
            iram[address - 0x03000000] = value;
        }
    }
    public short read16(int address) {
        int lowerByte = read8(address) & 0xFF;
        int upperByte = read8(address + 1) & 0xFF;
        return (short) ((upperByte << 8) | lowerByte);
    }
    public void write16(int address, short value) {
        write8(address, (byte) (value & 0xFF)); // 写入低 8 位
        write8(address + 1, (byte) ((value >> 8) & 0xFF)); // 写入高 8 位
    }


    // Read a 32-bit value from memory
    public int read32(int address) {
        return (read8(address) & 0xFF) |
                ((read8(address + 1) & 0xFF) << 8) |
                ((read8(address + 2) & 0xFF) << 16) |
                ((read8(address + 3) & 0xFF) << 24);
    }

    // Write a 32-bit value to memory
    public void write32(int address, int value) {
        write8(address, (byte) (value & 0xFF));
        write8(address + 1, (byte) ((value >> 8) & 0xFF));
        write8(address + 2, (byte) ((value >> 16) & 0xFF));
        write8(address + 3, (byte) ((value >> 24) & 0xFF));
    }
}
