package com.vadim.tkach.gym_tracker.controller;

import com.vadim.tkach.gym_tracker.config.SecurityUserIdResolver;
import com.vadim.tkach.gym_tracker.controller.dto.BestSetPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.FrequencyPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.ProgressDto;
import com.vadim.tkach.gym_tracker.controller.dto.VolumePointDto;

import com.vadim.tkach.gym_tracker.service.progress.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;
    private final SecurityUserIdResolver userIdResolver;

    @GetMapping("/{days}")
    public ProgressDto getProgress(@PathVariable int days,
                                   @RequestParam(required = false) UUID userId) {
        UUID uid = userIdResolver.requireUserId(userId);
        return progressService.getProgress(uid, days);
    }

    @GetMapping("/{days}/volume")
    public List<VolumePointDto> getVolumeSeries(@PathVariable int days,
                                                @RequestParam(required = false) UUID userId) {
        UUID uid = userIdResolver.requireUserId(userId);
        return progressService.getVolumeSeries(uid, days);
    }

    @GetMapping("/{days}/best-set")
    public List<BestSetPointDto> getBestSetSeries(@PathVariable int days,
                                                  @RequestParam(required = false) UUID userId) {
        UUID uid = userIdResolver.requireUserId(userId);
        return progressService.getBestSetSeries(uid, days);
    }

    @GetMapping("/{days}/frequency")
    public List<FrequencyPointDto> getFrequencySeries(@PathVariable int days,
                                                      @RequestParam(required = false) UUID userId) {
        UUID uid = userIdResolver.requireUserId(userId);
        return progressService.getFrequencySeries(uid, days);
    }
}
