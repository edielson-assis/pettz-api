package br.com.pettz.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "Get User Response DTO", description = "DTO to get an existing user")
public record UserResponse(
    
    @Schema(description = "User email", example = "maria@example.com")
    String email) {}