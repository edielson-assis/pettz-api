package br.com.pettz.dtos.response;

import br.com.pettz.models.User;

public record UserResponse(String email) {
    
    public UserResponse(User user) {
        this(user.getEmail());
    }
}