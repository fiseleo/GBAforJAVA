package MainApp;

public class CPU {
    private static final int NUM_REGISTERS = 16;
    private static final int PC = 15; // Program Counter
    private static final int CPSR = 16; // Current Program Status Register

    private int[] registers = new int[NUM_REGISTERS];
    private int cpsr;
    private Memory memory;
    private boolean thumbMode = false; // ARM (false) or Thumb (true) mode

    public CPU(Memory memory) {
        this.memory = memory;
        reset();
    }

    // Reset CPU state
    public void reset() {
        for (int i = 0; i < NUM_REGISTERS; i++) {
            registers[i] = 0;
        }
        cpsr = 0;
        thumbMode = false;
        // Set PC to the reset vector address
        registers[PC] = 0x00000000; // Typically, the reset vector is at address 0x00000000
    }

    // Fetch the next instruction
    private int fetch() {
        int pc = registers[PC];
        int instruction;
        if (thumbMode) {
            instruction = memory.read16(pc) & 0xFFFF;
            registers[PC] += 2;
        } else {
            instruction = memory.read32(pc);
            registers[PC] += 4;
        }
        return instruction;
    }
    private void executeAND(int instruction) {
        int rd = (instruction >> 12) & 0xF; // Destination register
        int rn = (instruction >> 16) & 0xF; // First operand register
        int operand2 = decodeOperand2(instruction); // Second operand
        registers[rd] = registers[rn] & operand2;
        updateFlags(registers[rd]); // Update CPSR flags
    }

    private void executeEOR(int instruction) {
        int rd = (instruction >> 12) & 0xF;
        int rn = (instruction >> 16) & 0xF;
        int operand2 = decodeOperand2(instruction);
        registers[rd] = registers[rn] ^ operand2;
        updateFlags(registers[rd]);
    }

    private void executeSUB(int instruction) {
        int rd = (instruction >> 12) & 0xF;
        int rn = (instruction >> 16) & 0xF;
        int operand2 = decodeOperand2(instruction);
        registers[rd] = registers[rn] - operand2;
        updateFlags(registers[rd]);
    }


    // Decode and execute the instruction
    private void decodeAndExecute(int instruction) {
        if (thumbMode) {
            executeThumbInstruction(instruction);
        } else {
            executeARMInstruction(instruction);
        }
    }

    // Execute ARM instruction
    private void executeARMInstruction(int instruction) {
        int condition = (instruction >>> 28) & 0xF;
        if (!checkCondition(condition)) {
            return; // Condition not met, skip instruction
        }

        int opcode = (instruction >>> 21) & 0xF;
        switch (opcode) {
            case 0x0: // AND
                executeAND(instruction);
                break;
            case 0x1: // EOR
                executeEOR(instruction);
                break;
            case 0x2: // SUB
                executeSUB(instruction);
                break;
            case 0x4: // ADD
                executeADD(instruction);
                break;
            // Add more opcodes as needed
            default:
                System.err.println("Unknown ARM opcode: " + opcode);
        }
    }

    // Execute Thumb instruction
    private void executeThumbInstruction(int instruction) {
        // Implement Thumb instruction decoding and execution
        // Thumb instructions are 16-bit wide and have different encoding
        // Refer to ARM7TDMI documentation for Thumb instruction set
    }

    // Check if the condition for the instruction is met
    private boolean checkCondition(int condition) {
        boolean z = (cpsr & (1 << 30)) != 0; // Zero flag
        boolean n = (cpsr & (1 << 31)) != 0; // Negative flag
        boolean c = (cpsr & (1 << 29)) != 0; // Carry flag
        boolean v = (cpsr & (1 << 28)) != 0; // Overflow flag

        switch (condition) {
            case 0x0: return z;             // EQ: Equal
            case 0x1: return !z;            // NE: Not equal
            case 0xA: return n == v;        // GE: Greater or equal
            case 0xB: return n != v;        // LT: Less than
            case 0xC: return !z && (n == v);// GT: Greater than
            case 0xD: return z || (n != v); // LE: Less or equal
            case 0xE: return true;          // AL: Always
            default:
                System.err.println("Unknown condition code: " + condition);
                return false;
        }
    }

    // Example implementation of the ADD instruction
    private void executeADD(int instruction) {
        int rd = (instruction >>> 12) & 0xF;
        int rn = (instruction >>> 16) & 0xF;
        int operand2 = decodeOperand2(instruction);
        int result = registers[rn] + operand2;
        registers[rd] = result;
        updateFlags(result);
    }

    // Decode Operand2 for data processing instructions
    private int decodeOperand2(int instruction) {
        boolean immediate = (instruction & (1 << 25)) != 0;
        if (immediate) {
            int imm = instruction & 0xFF;
            int rotate = (instruction >>> 8) & 0xF;
            return (imm >>> (rotate * 2)) | (imm << (32 - (rotate * 2)));
        } else {
            int rm = instruction & 0xF;
            // Implement shift operations if needed
            return registers[rm];
        }
    }

    // Update CPSR flags based on the result
    private void updateFlags(int result) {
        if (result == 0) {
            cpsr |= (1 << 30); // Set Zero flag
        } else {
            cpsr &= ~(1 << 30); // Clear Zero flag
        }
        if (result < 0) {
            cpsr |= (1 << 31); // Set Negative flag
        } else {
            cpsr &= ~(1 << 31); // Clear Negative flag
        }
        // Update Carry and Overflow flags as needed
    }

    // Execute one CPU cycle
    public void step() {
        int instruction = fetch();
        decodeAndExecute(instruction);
    }
}
