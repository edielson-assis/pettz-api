package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Register User Response DTO", description = "DTO for login and registering a new user")
public record UserResponse(
    
    UUID idUser, 
    
    @Schema(description = "Email of the user.", example = "maria@example.com")
    String email) {}