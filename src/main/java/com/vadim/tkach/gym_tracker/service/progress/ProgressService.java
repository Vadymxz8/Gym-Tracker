package com.vadim.tkach.gym_tracker.service.progress;


import com.vadim.tkach.gym_tracker.controller.dto.BestSetPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.FrequencyPointDto;
import com.vadim.tkach.gym_tracker.controller.dto.ProgressDto;
import com.vadim.tkach.gym_tracker.controller.dto.VolumePointDto;

import java.util.List;
import java.util.UUID;

public interface ProgressService {

    ProgressDto getProgress(UUID userId, int days);

    List<VolumePointDto> getVolumeSeries(UUID userId, int days);

    List<BestSetPointDto> getBestSetSeries(UUID userId, int days);

    List<FrequencyPointDto> getFrequencySeries(UUID userId, int days);
}

