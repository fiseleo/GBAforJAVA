package MainApp;

public class PPU {
    // GBA 屏幕分辨率
    private static final int SCREEN_WIDTH = 240;
    private static final int SCREEN_HEIGHT = 160;

    // 帧缓冲区，用于存储每一帧的像素数据
    private int[] frameBuffer;

    // 模拟图形寄存器
    private int bgColor; // 背景颜色

    public PPU() {
        // 初始化帧缓冲区
        frameBuffer = new int[SCREEN_WIDTH * SCREEN_HEIGHT];
        bgColor = 0xFF000000; // 默认黑色背景 (ARGB 格式)
    }

    // 设置背景颜色
    public void setBackgroundColor(int color) {
        this.bgColor = color;
    }

    // 渲染一帧
    public void renderFrame() {
        // 填充背景
        for (int y = 0; y < SCREEN_HEIGHT; y++) {
            for (int x = 0; x < SCREEN_WIDTH; x++) {
                frameBuffer[y * SCREEN_WIDTH + x] = bgColor; // 填充背景颜色
            }
        }

        // TODO: 添加精灵和图层渲染逻辑
        // 示例：渲染测试精灵
        renderTestSprite();
    }

    // 渲染一个测试精灵（仅用于验证渲染逻辑）
    private void renderTestSprite() {
        int spriteWidth = 16;
        int spriteHeight = 16;
        int spriteColor = 0xFFFF0000; // 红色 (ARGB 格式)
        int spriteX = 100; // 精灵位置
        int spriteY = 60;

        for (int y = 0; y < spriteHeight; y++) {
            for (int x = 0; x < spriteWidth; x++) {
                int posX = spriteX + x;
                int posY = spriteY + y;
                if (posX < SCREEN_WIDTH && posY < SCREEN_HEIGHT) {
                    frameBuffer[posY * SCREEN_WIDTH + posX] = spriteColor;
                }
            }
        }
    }

    // 获取当前帧缓冲区
    public int[] getFrameBuffer() {
        return frameBuffer;
    }
}
