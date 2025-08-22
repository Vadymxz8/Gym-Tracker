package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.service.workout.WorkoutService;
import com.vadim.tkach.gym_tracker.service.model.Workout;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

//    @PostMapping
//    public ResponseEntity<Void> createWorkout(@RequestBody Workout workout) {
//        workoutService.createWorkout(workout);
//        return ResponseEntity.status(201).build();
//    }
@PostMapping
public ResponseEntity<Void> createWorkout(@RequestBody Workout workout) {
    log.info("POST /api/workouts payload: {}", workout);
    if (workout == null) {
        log.warn("Received null workout body");
        return ResponseEntity.badRequest().build();
    }
    if (workout.getUserId() == null) {
        log.warn("Missing userId in incoming workout: {}", workout);
        return ResponseEntity.badRequest().body(null);
    }
    workoutService.createWorkout(workout);
    return ResponseEntity.status(201).build();
}

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        return ResponseEntity.ok(workoutService.getAllWorkouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable UUID id) {
        return ResponseEntity.ok(workoutService.getWorkoutById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWorkout(@PathVariable UUID id, @RequestBody Workout workout) {
        workout.setId(id); // важливо, бо id йде з path
        workoutService.updateWorkout(workout);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable UUID id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(workoutService.getWorkoutsByUserId(userId));
    }
}

