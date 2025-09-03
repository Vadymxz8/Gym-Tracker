package com.vadim.tkach.gym_tracker.progress;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProgressCalculatorTest {

    @Test
    void testOneRepMax() {
        double est = ProgressCalculator.estimateOneRepMax(100, 10);
        assertEquals(133.33, est, 0.01);
    }

    @Test
    void testSetVolume() {
        double volume = ProgressCalculator.setVolume(10, 100.0, 3);
        assertEquals(3000.0, volume, 0.01);
    }

    @Test
    void testChangePct() {
        assertEquals(10.0, ProgressCalculator.changePct(110, 100), 0.0001);
    }

    @Test
    void testStagnation() {
        assertTrue(ProgressCalculator.isStagnating(0.5, -15));
        assertFalse(ProgressCalculator.isStagnating(5, -5));
    }
}
