package com.vadim.tkach.gym_tracker.service.token;


import com.vadim.tkach.gym_tracker.service.domain.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Override
    public String createToken(User user) {
        // Тимчасова реалізація. Можна замінити на JWT або інший механізм
        return UUID.randomUUID().toString();
    }
}
