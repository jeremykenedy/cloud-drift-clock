package com.jeremykenedy.clouddriftclock;

public final class SettingsValues {
    private SettingsValues() {}

    public static boolean isSupported(String key, String value) {
        if (key == null || value == null) return false;
        if ("palette".equals(key)) return oneOf(value, "day", "sunset", "night", "random");
        if ("speed".equals(key)) return oneOf(value, "slow", "gentle", "drifting", "random");
        if ("density".equals(key)) return oneOf(value, "light", "balanced", "full", "random");
        if ("clock_size".equals(key)) return oneOf(value, "subtle", "standard", "large", "random");
        if ("clock_drift".equals(key)) return oneOf(value, "anchored", "short", "wide", "random");
        if ("clock_format".equals(key)) return oneOf(value, "12h", "24h", "random");
        if ("show_seconds".equals(key)) return oneOf(value, "true", "false", "random");
        if ("randomize_all".equals(key)) {
            return oneOf(value, "true", "false");
        }
        return false;
    }

    private static boolean oneOf(String value, String... allowed) {
        for (String option : allowed) if (option.equals(value)) return true;
        return false;
    }
}
