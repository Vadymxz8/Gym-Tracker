package com.vadim.tkach.gym_tracker.service.token;

import com.vadim.tkach.gym_tracker.service.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.ttl-millis}")
    private Long jwtTtlMillis;

    @Override
    public String createToken(User user) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtTtlMillis);

        Claims claims = Jwts.claims()
                .issuedAt(now)
                .expiration(expiration)
                .subject(user.getId().toString())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(getSecretKey())
                .compact();
    }

    @Override
    public boolean isValidToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public UUID getUserId(String token) {
        String subject = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return UUID.fromString(subject);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
