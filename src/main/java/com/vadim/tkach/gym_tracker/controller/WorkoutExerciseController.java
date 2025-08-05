package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.workout.WorkoutExerciseService;
import com.vadim.tkach.gym_tracker.service.model.WorkoutExercise;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workout-exercises")
@RequiredArgsConstructor
public class WorkoutExerciseController {

    private final WorkoutExerciseService workoutExerciseService;

    @PostMapping
    public ResponseEntity<Void> addWorkoutExercise(@RequestBody WorkoutExercise workoutExercise) {
        workoutExerciseService.addWorkoutExercise(workoutExercise);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<WorkoutExercise>> getAllWorkoutExercises() {
        List<WorkoutExercise> exercises = workoutExerciseService.getAllWorkoutExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutExercise> getWorkoutExercise(@PathVariable UUID id) {
        WorkoutExercise exercise = workoutExerciseService.getWorkoutExercise(id);
        return ResponseEntity.ok(exercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutExercise(@PathVariable UUID id) {
        workoutExerciseService.deleteWorkoutExercise(id);
        return ResponseEntity.noContent().build();
    }
}
