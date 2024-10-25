package br.com.pettz.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@Schema(description = "Represents the User's Signup")
public record UserSignupRequest(

    @Schema(description = "Full name of the person", example = "Robert Martin", maxLength = 150, required = true)
    @NotBlank(message = "FullName is required")
    @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s']+$", message = "Only letters should be typed")
    @Size(min = 3, message = "FullName must have at least {min} characters")
    String fullName,

    @Schema(description = "Email of the user.", example = "robert@example.com", maxLength = 100, required = true)
    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email,

    @Schema(description = "Password for the user account.", example = "372@RfI5n&Ml", maxLength = 255, required = true)
    @NotBlank(message = "Password is required")
    String password
) {}