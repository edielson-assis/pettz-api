package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Register User Response DTO", description = "DTO to log in and register a new user")
public record UserResponse(
    
    UUID idUser, 
    
    @Schema(description = "User email", example = "maria@example.com")
    String email) {}