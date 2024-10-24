package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Represents the User's Signin")
public record UserSigninRequest(

    @Schema(description = "Email of the user.", example = "robert@example.com", maxLength = 100, required = true)
    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email,

    @Schema(description = "Password for the user account.", example = "372@RfI5n&Ml", maxLength = 255, required = true)
    @NotBlank(message = "Password is required")
    String password
) {}