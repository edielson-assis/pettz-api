package br.com.pettz.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.pettz.models.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(User user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            log.info("Generating JWT token");
            return JWT.create()
                    .withIssuer("Pettz API")
                    .withSubject(user.getUsername())
                    .withExpiresAt(exprirationToken())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            log.error("Error generating JWT token.", exception.getMessage());
            throw new SecurityException("Error generating JWT token", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            log.info("Verifying JWT token: {}", tokenJWT);
            return JWT.require(algorithm)
                    .withIssuer("Pettz API")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.error("Invalid or expired JWT token!", exception.getMessage());
            throw new SecurityException("Invalid or expired JWT token!");
        }
    }

    private Instant exprirationToken() {
        return LocalDateTime.now().plusHours(12).toInstant(ZoneOffset.of("-03:00"));
    }
}