package br.com.pettz.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(

    @NotBlank(message = "Email is required") 
    @Email(message = "Invalid email")
    String email,

    @NotBlank(message = "Password is required")
    String password
) {}