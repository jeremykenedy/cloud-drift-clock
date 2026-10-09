package com.jeremykenedy.clouddriftclock;

final class CloudLayout {
    CloudLayout() {}

    static int sourceTop(int cloudCount, int height) {
        if (cloudCount == 8) return 0;
        if (cloudCount == 14) return height / 9;
        return height / 4;
    }

    static int foregroundAlpha(int cloudCount) {
        if (cloudCount == 8) return 0;
        return cloudCount == 14 ? 38 : 66;
    }

    static float cameraX(float time, float speed, int width) {
        return (float) Math.sin(time * 0.035f * speed) * width * 0.014f;
    }

    static float cameraY(float time, float speed, int height) {
        return (float) Math.cos(time * 0.022f * speed) * height * 0.008f;
    }

    static float foregroundX(float time, float speed, int width) {
        return (float) Math.sin(time * 0.027f * speed) * width * 0.025f;
    }

    static float clockX(float time, int width, int drift) {
        return width * 0.5f + (float) Math.sin(time * 0.035f) * drift * width * 0.18f;
    }

    static float clockY(float time, int height, int drift) {
        return height * 0.46f + (float) Math.cos(time * 0.027f) * drift * height * 0.11f;
    }

    static String timePattern(boolean use24Hour, boolean showSeconds) {
        if (use24Hour) return showSeconds ? "HH:mm:ss" : "HH:mm";
        return showSeconds ? "h:mm:ss" : "h:mm";
    }

    static int clockColor(int palette) {
        return palette == 2 ? 0xfff5f2ed : 0xff203449;
    }

    static int shadowColor(int palette) {
        return palette == 2 ? 0x99071122 : 0xbfffffff;
    }

    static int paletteOverlay(int palette) {
        if (palette == 1) return 0x2bf2a56f;
        if (palette == 2) return 0x6b101b39;
        return 0;
    }
}
