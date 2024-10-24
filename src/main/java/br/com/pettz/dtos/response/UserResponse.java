package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Represents a User")
public record UserResponse(
    
    @Schema(description = "Unique identifier of the user", example = "1")
    UUID userId,

    @Schema(description = "Full name of the person", example = "Robert Martin")
    String fullName,

    @Schema(description = "Email of the user.", example = "robert@example.com")
    String email
) {}