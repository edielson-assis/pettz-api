package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register User Request DTO", description = "DTO to log in and register a new user")
public record UserRequest(

    @Schema(description = "User email", example = "maria@example.com")
    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email,

    @Schema(description = "Password for the user account.", example = "372@RfI5n&Ml")
    @NotBlank(message = "Password is required")
    String password
) {}