package com.jeremykenedy.clouddriftclock;

import java.util.Random;

public final class ClockOptions {
    public final int palette;
    public final float cloudSpeed;
    public final int cloudCount;
    public final int clockSize;
    public final int drift;
    public final boolean use24Hour;
    public final boolean showSeconds;

    private ClockOptions(int palette, float cloudSpeed, int cloudCount, int clockSize,
            int drift, boolean use24Hour, boolean showSeconds) {
        this.palette = palette;
        this.cloudSpeed = cloudSpeed;
        this.cloudCount = cloudCount;
        this.clockSize = clockSize;
        this.drift = drift;
        this.use24Hour = use24Hour;
        this.showSeconds = showSeconds;
    }

    public static ClockOptions resolve(String palette, String speed, String density,
            String size, String drift, String format, String seconds, boolean randomizeAll,
            Random random) {
        return new ClockOptions(
                paletteValue(choose(palette, randomizeAll, random, "day", "sunset", "night")),
                speedValue(choose(speed, randomizeAll, random, "slow", "gentle", "drifting")),
                densityValue(choose(density, randomizeAll, random, "light", "balanced", "full")),
                sizeValue(choose(size, randomizeAll, random, "subtle", "standard", "large")),
                driftValue(choose(drift, randomizeAll, random, "anchored", "short", "wide")),
                "24h".equals(choose(format, randomizeAll, random, "12h", "24h")),
                "true".equals(choose(seconds, randomizeAll, random, "false", "true")));
    }

    private static String choose(String selected, boolean randomizeAll, Random random,
            String... values) {
        if (randomizeAll || "random".equals(selected)) return values[random.nextInt(values.length)];
        for (String value : values) if (value.equals(selected)) return value;
        return values[0];
    }

    private static int paletteValue(String value) {
        if ("sunset".equals(value)) return 1;
        if ("night".equals(value)) return 2;
        return 0;
    }

    private static float speedValue(String value) {
        if ("slow".equals(value)) return 0.55f;
        if ("drifting".equals(value)) return 1.35f;
        return 0.85f;
    }

    private static int densityValue(String value) {
        if ("light".equals(value)) return 8;
        if ("full".equals(value)) return 20;
        return 14;
    }

    private static int sizeValue(String value) {
        if ("subtle".equals(value)) return 48;
        if ("large".equals(value)) return 78;
        return 62;
    }

    private static int driftValue(String value) {
        if ("anchored".equals(value)) return 0;
        if ("wide".equals(value)) return 2;
        return 1;
    }
}
