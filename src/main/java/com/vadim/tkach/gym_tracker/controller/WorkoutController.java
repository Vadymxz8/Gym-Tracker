package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Minimal controller exposing workouts for smoke tests and progress queries.
 */
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Workout> all() {
        String sql = "SELECT id, date, name, workout_type, note, user_id FROM workouts";
        return jdbcTemplate.query(sql, this::mapWorkout);
    }

    private Workout mapWorkout(ResultSet rs, int rowNum) throws SQLException {
        return new Workout(
                UUID.fromString(rs.getString("id")),
                rs.getDate("date").toLocalDate(),
                rs.getString("name"),
                rs.getString("workout_type"),
                rs.getString("note"),
                UUID.fromString(rs.getString("user_id"))
        );
    }
}

