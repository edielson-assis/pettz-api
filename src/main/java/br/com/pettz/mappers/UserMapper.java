package br.com.pettz.mappers;

import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.models.enums.Role;

public class UserMapper {

    private UserMapper() {}
    
    public static User toEntity(UserSignupRequest userRequest) {
        User user = new User();
        user.setFullName(userRequest.fullName());
        user.setEmail(userRequest.email());
        user.setPassword(userRequest.password());
        user.setRole(Role.USER);
        return user;
    }

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .fullName(user.getFullName())
                .email(user.getEmail()).build();
    }
}