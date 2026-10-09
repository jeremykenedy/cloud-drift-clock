package com.jeremykenedy.clouddriftclock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Random;
import org.junit.Test;

public final class ClockOptionsTest {
    @Test
    public void positionsCloudsAndClockWithinTheirExpectedRanges() {
        new CloudLayout();
        assertEquals(0, CloudLayout.sourceTop(8, 1080));
        assertEquals(120, CloudLayout.sourceTop(14, 1080));
        assertEquals(270, CloudLayout.sourceTop(20, 1080));
        assertEquals(0, CloudLayout.foregroundAlpha(8));
        assertEquals(38, CloudLayout.foregroundAlpha(14));
        assertEquals(66, CloudLayout.foregroundAlpha(20));
        assertEquals(960f, CloudLayout.clockX(2f, 1920, 0), 0.01f);
        assertEquals(496.8f, CloudLayout.clockY(2f, 1080, 0), 0.01f);
        assertTrue(CloudLayout.clockX(2f, 1920, 1) > 960f);
        assertTrue(CloudLayout.clockY(2f, 1080, 1) > 496.8f);
        assertEquals(0f, CloudLayout.cameraX(0f, 1f, 1920), 0f);
        assertEquals(8.64f, CloudLayout.cameraY(0f, 1f, 1080), 0.01f);
        assertEquals(0f, CloudLayout.foregroundX(0f, 1f, 1920), 0f);
        assertTrue(CloudLayout.cameraX(2f, 1.3f, 1920) > CloudLayout.cameraX(2f, 0.5f, 1920));
    }

    @Test
    public void mapsClockTextAndPaletteColors() {
        assertEquals("h:mm", CloudLayout.timePattern(false, false));
        assertEquals("h:mm:ss", CloudLayout.timePattern(false, true));
        assertEquals("HH:mm", CloudLayout.timePattern(true, false));
        assertEquals("HH:mm:ss", CloudLayout.timePattern(true, true));
        assertEquals(0xff203449, CloudLayout.clockColor(0));
        assertEquals(0xff203449, CloudLayout.clockColor(1));
        assertEquals(0xfff5f2ed, CloudLayout.clockColor(2));
        assertEquals(0xbfffffff, CloudLayout.shadowColor(0));
        assertEquals(0xbfffffff, CloudLayout.shadowColor(1));
        assertEquals(0x99071122, CloudLayout.shadowColor(2));
        assertEquals(0, CloudLayout.paletteOverlay(0));
        assertEquals(0x2bf2a56f, CloudLayout.paletteOverlay(1));
        assertEquals(0x6b101b39, CloudLayout.paletteOverlay(2));
    }

    @Test
    public void mapsEverySupportedSceneAndClockChoice() {
        assertOptions("day", "slow", "light", "subtle", "anchored", "12h", "false", 0, 0.55f, 8, 48, 0, false, false);
        assertOptions("sunset", "gentle", "balanced", "standard", "short", "12h", "true", 1, 0.85f, 14, 62, 1, false, true);
        assertOptions("night", "drifting", "full", "large", "wide", "24h", "true", 2, 1.35f, 20, 78, 2, true, true);
    }

    @Test
    public void resolvesRandomChoicesAndRandomizeAllDeterministically() {
        boolean sawSeconds = false;
        boolean sawNoSeconds = false;
        for (int i = 0; i < 12; i++) {
            ClockOptions random = ClockOptions.resolve("random", "random", "random", "random",
                    "random", "random", "random", false, new Random(i));
            assertTrue(random.palette >= 0 && random.palette <= 2);
            assertTrue(random.cloudSpeed >= 0.55f && random.cloudSpeed <= 1.35f);
            assertTrue(random.cloudCount == 8 || random.cloudCount == 14 || random.cloudCount == 20);
            assertTrue(random.clockSize == 48 || random.clockSize == 62 || random.clockSize == 78);
            assertTrue(random.drift >= 0 && random.drift <= 2);
            sawSeconds |= random.showSeconds;
            sawNoSeconds |= !random.showSeconds;
        }
        assertTrue(sawSeconds && sawNoSeconds);
        ClockOptions all = ClockOptions.resolve("day", "slow", "light", "subtle", "anchored",
                "12h", "false", true, new Random(15));
        assertTrue(all.palette >= 0 && all.palette <= 2);
        assertTrue(all.cloudCount == 8 || all.cloudCount == 14 || all.cloudCount == 20);
    }

    @Test
    public void invalidChoicesUseDocumentedDefaults() {
        ClockOptions options = ClockOptions.resolve("bad", "bad", "bad", "bad", "bad", "bad",
                "bad", false, new Random(1));
        assertEquals(0, options.palette);
        assertEquals(0.55f, options.cloudSpeed, 0.0f);
        assertEquals(8, options.cloudCount);
        assertEquals(48, options.clockSize);
        assertEquals(0, options.drift);
        assertFalse(options.use24Hour);
        assertFalse(options.showSeconds);
    }

    @Test
    public void validatesProviderValuesAndRejectsUnknownInputs() {
        String[][] supported = {
            {"palette", "day", "sunset", "night", "random"},
            {"speed", "slow", "gentle", "drifting", "random"},
            {"density", "light", "balanced", "full", "random"},
            {"clock_size", "subtle", "standard", "large", "random"},
            {"clock_drift", "anchored", "short", "wide", "random"},
            {"clock_format", "12h", "24h", "random"},
            {"show_seconds", "true", "false", "random"},
            {"randomize_all", "true", "false"}
        };
        for (String[] row : supported) {
            for (int i = 1; i < row.length; i++) assertTrue(SettingsValues.isSupported(row[0], row[i]));
        }
        assertFalse(SettingsValues.isSupported(null, "day"));
        assertFalse(SettingsValues.isSupported("palette", null));
        assertFalse(SettingsValues.isSupported("palette", "midnight"));
        assertFalse(SettingsValues.isSupported("unknown", "day"));
    }

    private static void assertOptions(String palette, String speed, String density, String size,
            String drift, String format, String seconds, int expectedPalette, float expectedSpeed,
            int expectedCount, int expectedSize, int expectedDrift, boolean expected24Hour,
            boolean expectedSeconds) {
        ClockOptions options = ClockOptions.resolve(palette, speed, density, size, drift, format,
                seconds, false, new Random(1));
        assertEquals(expectedPalette, options.palette);
        assertEquals(expectedSpeed, options.cloudSpeed, 0.0f);
        assertEquals(expectedCount, options.cloudCount);
        assertEquals(expectedSize, options.clockSize);
        assertEquals(expectedDrift, options.drift);
        assertEquals(expected24Hour, options.use24Hour);
        assertEquals(expectedSeconds, options.showSeconds);
    }
}
