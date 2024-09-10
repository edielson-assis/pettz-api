package br.com.pettz.mappers;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.models.enums.Role;

public class UserMapper {

    private UserMapper() {}
    
    public static User toEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.email())
                .password(userRequest.password())
                .role(Role.USER).build();
    }

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .email(user.getEmail()).build();
    }
}