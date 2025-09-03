package com.vadim.tkach.gym_tracker.progress;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @GetMapping("/progress/summary")
    public ProgressSummaryResponse summary(@RequestParam UUID userId,
                                           @RequestParam String range) {
        return progressService.getSummary(userId, range);
    }

    @GetMapping("/progress/exercise")
    public List<ExerciseProgressDto> exercise(@RequestParam UUID userId,
                                              @RequestParam UUID exerciseId,
                                              @RequestParam String range) {
        return progressService.getExerciseProgress(userId, exerciseId, range);
    }

    @GetMapping("/sessions")
    public List<SessionDto> sessions(@RequestParam UUID userId,
                                     @RequestParam(required = false) String cursor,
                                     @RequestParam(defaultValue = "10") int limit) {
        LocalDate cursorDate = cursor == null ? null : LocalDate.parse(cursor);
        return progressService.getSessions(userId, cursorDate, limit);
    }
}

