package br.com.pettz.security;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JWT Response DTO", description = "DTO for JWT token")
public record TokenJWT(
    
    @Schema(description = "Token provided in the response.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJDb2xoZWl0YSBGc...")
    String token) {}