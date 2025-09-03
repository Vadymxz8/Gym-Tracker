package com.vadim.tkach.gym_tracker.progress;

/**
 * Utility methods for progress related calculations.
 */
public final class ProgressCalculator {

    private ProgressCalculator() {
    }

    /**
     * Epley 1RM estimation.
     */
    public static double estimateOneRepMax(double weight, int reps) {
        return weight * (1 + reps / 30.0);
    }

    /**
     * Calculates volume for a set.
     */
    public static double setVolume(int reps, Double weight, int sets) {
        double w = weight == null ? 0d : weight;
        int s = sets == 0 ? 1 : sets;
        return reps * w * s;
    }

    /**
     * Percentage change between current and previous value.
     */
    public static double changePct(double current, double previous) {
        if (previous == 0d) {
            return current == 0d ? 0d : 100d;
        }
        return (current - previous) / previous * 100d;
    }

    /**
     * Determines stagnation based on given changes.
     */
    public static boolean isStagnating(double estChangePct, double volumeChangePct) {
        return estChangePct < 1d || volumeChangePct <= -10d;
    }
}

