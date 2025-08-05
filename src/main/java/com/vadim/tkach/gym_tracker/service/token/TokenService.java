package com.vadim.tkach.gym_tracker.service.token;

import com.vadim.tkach.gym_tracker.service.model.User;
import org.hibernate.usertype.UserType;

import java.util.UUID;

public interface TokenService {

    String createToken(User user);

    boolean isValidToken(String token);

    UUID getUserId(String token);

}

