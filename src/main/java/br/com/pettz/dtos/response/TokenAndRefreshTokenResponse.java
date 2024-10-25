package br.com.pettz.dtos.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Representation of a JWT Token and Refresh token")
public record TokenAndRefreshTokenResponse(

    @Schema(description = "User ID", example = "99ac1044-0c6e-4950-bfd4-76a2f3e074ae")
    UUID idUser,

    @Schema(description = "Full name of the person", example = "Robert Martin")
    String fullName,

    @Schema(description = "Access token provided for authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    String accessToken,

    @Schema(description = "Refresh token provided for re-authentication", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    String refreshToken
) {}