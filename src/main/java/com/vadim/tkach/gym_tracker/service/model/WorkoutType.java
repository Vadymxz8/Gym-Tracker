package com.vadim.tkach.gym_tracker.service.model;

public enum WorkoutType {
    FULLBODY,
    UPPER_LOWER,
    SPLIT;

    public static WorkoutType fromCode(String code) {
        for (WorkoutType type : values()) {
            if (type.name().equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown workout type: " + code);
    }
}


