package com.vadim.tkach.gym_tracker.config;

import com.vadim.tkach.gym_tracker.service.token.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final TokenService tokenService;

    // ✅ Whitelist — ендпоінти, які не вимагають токена
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/users/login",
            "/users/signup",
            "/users/registration",
            "/users/registration-complete",
            "/users/passwords/reset",
            "/users/passwords/reset-verify",
            "/users/passwords/reset-complete"
    );

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        String path = request.getServletPath();

        // Якщо endpoint у whitelist — пропускаємо без перевірки токена
        if (isPublicEndpoint(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwt = getJwtFromRequest(request);

        if (!StringUtils.hasText(jwt) || !tokenService.isValidToken(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        UUID userId = tokenService.getUserId(jwt);

        UserDetails userDetails = new User(
                userId.toString(),
                "", // пароль не потрібен у JWT-фільтрі
                Collections.emptyList() // ролі можна додати за потреби
        );

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);

        new RequestAttributeSecurityContextRepository()
                .saveContext(securityContext, request, response);

        filterChain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream().anyMatch(path::startsWith);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TOKEN_PREFIX)) {
            return bearerToken.substring(BEARER_TOKEN_PREFIX.length());
        }
        return null;
    }
}
