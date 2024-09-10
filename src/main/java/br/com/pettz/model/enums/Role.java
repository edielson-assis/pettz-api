package br.com.pettz.model.enums;

import lombok.Getter;

@Getter
public enum Role {
    
    ADMIN("Admin"),
    USER("User");

    private String name;

    private Role(String role) {
        this.name = role;
    }
}