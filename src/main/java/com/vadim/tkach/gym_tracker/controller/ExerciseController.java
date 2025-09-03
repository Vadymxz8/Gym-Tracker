package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.domain.Exercise;
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
 * Minimal controller exposing exercises for smoke tests and progress queries.
 */
@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping
    public List<Exercise> all() {
        String sql = "SELECT id, name, type, muscle_group FROM exercises";
        return jdbcTemplate.query(sql, this::mapExercise);
    }

    private Exercise mapExercise(ResultSet rs, int rowNum) throws SQLException {
        return new Exercise(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("type"),
                rs.getString("muscle_group")
        );
    }
}

