package com.vadim.tkach.gym_tracker.config;


import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Component
public class SecurityUserIdResolver {

    /**
     * Якщо userId передали як query-параметр — беремо його.
     * Інакше — дістаємо з SecurityContext (TokenAuthFilter вже поклав туди userId як username).
     */
    public UUID requireUserId(UUID userIdParamOrNull) {
        if (userIdParamOrNull != null) return userIdParamOrNull;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
        try {
            return UUID.fromString(auth.getName());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid authenticated principal");
        }
    }
}
