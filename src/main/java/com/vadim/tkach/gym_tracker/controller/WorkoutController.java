package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.WorkoutService;
import com.vadim.tkach.gym_tracker.service.domain.Workout;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    // 🟢 Створити тренування
    @PostMapping
    public ResponseEntity<Void> createWorkout(@RequestBody Workout workout) {
        workoutService.createWorkout(workout);
        return ResponseEntity.status(201).build();
    }

    // 🟡 Отримати всі тренування
    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    // 🔵 Отримати тренування по id
    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    // 🟠 Оновити тренування
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWorkout(@PathVariable UUID id, @RequestBody Workout workout) {
        workout.setId(id); // важливо, бо id йде з path
        workoutService.updateWorkout(workout);
        return ResponseEntity.noContent().build();
    }

    // 🔴 Видалити тренування
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    // 🟣 Отримати всі тренування користувача
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }
}

