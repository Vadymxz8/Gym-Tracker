package com.vadim.tkach.gym_tracker.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProgressRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<ProgressDay> dailySummary(UUID userId, LocalDate from, LocalDate to) {
        String sql = "SELECT w.date AS d, " +
                "COUNT(DISTINCT w.id) AS sessions, " +
                "COALESCE(SUM(we.reps * COALESCE(we.weight,0) * COALESCE(we.sets,1)),0) AS volume, " +
                "MAX(we.weight * (1 + we.reps/30.0)) AS est1rm " +
                "FROM workouts w " +
                "LEFT JOIN workout_exercise we ON we.workout_id = w.id " +
                "WHERE w.user_id = ? AND w.date BETWEEN ? AND ? " +
                "GROUP BY w.date ORDER BY w.date";

        return jdbcTemplate.query(sql, new Object[]{userId, from, to}, this::mapDay);
    }

    public List<ExerciseProgressDto> exerciseProgress(UUID userId, UUID exerciseId, LocalDate from, LocalDate to) {
        String sql = "SELECT w.date AS d, " +
                "COALESCE(SUM(we.reps * COALESCE(we.weight,0) * COALESCE(we.sets,1)),0) AS volume, " +
                "MAX(we.weight * (1 + we.reps/30.0)) AS est1rm " +
                "FROM workouts w " +
                "JOIN workout_exercise we ON we.workout_id = w.id " +
                "WHERE w.user_id = ? AND we.exercise_id = ? AND w.date BETWEEN ? AND ? " +
                "GROUP BY w.date ORDER BY w.date";

        return jdbcTemplate.query(sql, new Object[]{userId, exerciseId, from, to}, this::mapExerciseProgress);
    }

    public List<SessionDto> sessions(UUID userId, LocalDate cursor, int limit) {
        String sql = "SELECT id, date, name, note FROM workouts " +
                "WHERE user_id = ? " +
                "AND (? IS NULL OR date < ?) " +
                "ORDER BY date DESC LIMIT ?";
        return jdbcTemplate.query(sql, new Object[]{userId, cursor, cursor, limit}, this::mapSession);
    }

    private ProgressDay mapDay(ResultSet rs, int rowNum) throws SQLException {
        return new ProgressDay(
                rs.getDate("d").toLocalDate(),
                rs.getDouble("volume"),
                rs.getInt("sessions"),
                rs.getDouble("est1rm")
        );
    }

    private ExerciseProgressDto mapExerciseProgress(ResultSet rs, int rowNum) throws SQLException {
        return new ExerciseProgressDto(
                rs.getDate("d").toLocalDate(),
                rs.getDouble("est1rm"),
                rs.getDouble("volume")
        );
    }

    private SessionDto mapSession(ResultSet rs, int rowNum) throws SQLException {
        return new SessionDto(
                UUID.fromString(rs.getString("id")),
                rs.getDate("date").toLocalDate(),
                rs.getString("name"),
                rs.getString("note")
        );
    }
}

