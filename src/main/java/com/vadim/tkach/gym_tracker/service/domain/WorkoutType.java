package com.vadim.tkach.gym_tracker.service.domain;
public enum WorkoutType {
    //    FULLBODY("ФУЛБАДІ"),
//    UPPER_LOWER("ВЕРХ_НИЗ"),
//    SPLIT("СПЛІТ");
//
//    private final String dbValue;
//
//    WorkoutType(String dbValue) {
//        this.dbValue = dbValue;
//    }
//
//    public String getDbValue() {
//        return dbValue;
//    }
//
//    public static WorkoutType fromDbValue(String dbValue) {
//        for (WorkoutType type : values()) {
//            if (type.dbValue.equalsIgnoreCase(dbValue)) {
//                return type;
//            }
//        }
//        throw new IllegalArgumentException("Unknown workout type: " + dbValue);
//    }
//
//    @Override
//    public String toString() {
//        return dbValue;
//    }
//}
    FULLBODY("FULLBODY", "ФУЛБАДІ"),
    UPPER_LOWER("UPPER_LOWER", "ВЕРХ_НИЗ"),
    SPLIT("SPLIT", "СПЛІТ");

    private final String code;
    private final String displayName;

    WorkoutType(String code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static WorkoutType fromCode(String code) {
        for (WorkoutType type : values()) {
            if (type.code.equalsIgnoreCase(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown workout type: " + code);
    }

    @Override
    public String toString() {
        return displayName;
    }
}

