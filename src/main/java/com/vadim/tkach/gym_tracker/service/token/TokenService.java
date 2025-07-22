package com.vadim.tkach.gym_tracker.service.token;

import com.vadim.tkach.gym_tracker.service.domain.User;

public interface TokenService {
    String createToken(User user);
}

