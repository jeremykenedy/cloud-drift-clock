package com.jeremykenedy.clouddriftclock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.Test;

public final class FlightOptionsTest {
    @Test public void mapsStoredChoicesToTheLabelledPlaybackRates() {
        assertEquals(0.75f, FlightOptions.resolve("slow", false, new Random(1)).playbackSpeed, 0f);
        assertEquals(1f, FlightOptions.resolve("gentle", false, new Random(1)).playbackSpeed, 0f);
        assertEquals(1.25f, FlightOptions.resolve("drifting", false, new Random(1)).playbackSpeed, 0f);
        assertEquals(1f, FlightOptions.resolve("invalid", false, new Random(1)).playbackSpeed, 0f);
        assertEquals(1f, FlightOptions.resolve(null, false, new Random(1)).playbackSpeed, 0f);
    }

    @Test public void randomizationSelectsOnlySupportedPlaybackRates() {
        Set<Float> rates = new HashSet<>();
        for (int seed = 0; seed < 256; seed++) {
            FlightOptions explicit = FlightOptions.resolve("random", false, new Random(seed * 7919));
            FlightOptions all = FlightOptions.resolve("slow", true, new Random(seed * 7919));
            assertEquals(explicit.playbackSpeed, all.playbackSpeed, 0f);
            rates.add(explicit.playbackSpeed);
        }
        assertEquals(3, rates.size());
        assertTrue(rates.contains(0.75f));
        assertTrue(rates.contains(1f));
        assertTrue(rates.contains(1.25f));
    }

    @Test public void keepsLegacyChoicesAndClockVisibilityIndependent() {
        assertTrue(SettingsValues.isSupported("palette", "night"));
        assertTrue(SettingsValues.isSupported("density", "full"));
        for (boolean show : new boolean[] {false, true}) {
            ClockOptions clock = ClockOptions.resolve("day", "gentle", "balanced", "standard", "short",
                    "12h", "false", show, true, new Random(1));
            assertEquals(show, clock.showClock);
        }
    }
}
