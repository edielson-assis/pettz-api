package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(name = "Register User Request DTO", description = "DTO for login and registering a new user")
public record UserRequest(

    @Schema(description = "Email of the user.", example = "maria@example.com")
    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email,

    @Schema(description = "Password for the user account.", example = "372@RfI5n&Ml")
    @NotBlank(message = "Password is required")
    String password
) {}