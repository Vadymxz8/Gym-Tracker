package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.exercise.ExerciseService;
import com.vadim.tkach.gym_tracker.service.model.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Void> createExercise(@RequestBody Exercise exercise) {
        exerciseService.createExercise(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExercise(@PathVariable UUID id) {
        Exercise exercise = exerciseService.getExercise(id);
        return ResponseEntity.ok(exercise);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExercise(@PathVariable UUID id, @RequestBody Exercise exercise) {
        exercise.setId(id);
        exerciseService.updateExercise(exercise);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable UUID id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }
}

