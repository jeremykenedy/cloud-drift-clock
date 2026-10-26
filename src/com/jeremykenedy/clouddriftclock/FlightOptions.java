package com.jeremykenedy.clouddriftclock;

import java.util.Random;

final class FlightOptions {
    final float playbackSpeed;

    private FlightOptions(float playbackSpeed) {
        this.playbackSpeed = playbackSpeed;
    }

    static FlightOptions resolve(String speed, boolean randomizeAll, Random random) {
        String selectedSpeed = choose(speed, randomizeAll, random, "gentle", "slow", "drifting");
        float rate = "slow".equals(selectedSpeed) ? 0.75f : "drifting".equals(selectedSpeed) ? 1.25f : 1f;
        return new FlightOptions(rate);
    }

    private static String choose(String selected, boolean randomizeAll, Random random, String... values) {
        if (randomizeAll || "random".equals(selected)) return values[random.nextInt(values.length)];
        for (String value : values) if (value.equals(selected)) return value;
        return values[0];
    }
}
