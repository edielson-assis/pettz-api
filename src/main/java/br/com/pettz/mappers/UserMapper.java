package br.com.pettz.mappers;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.models.enums.Role;

public class UserMapper {

    private static final boolean IS_ACCOUNT_NON_EXPIRED = true;
    private static final boolean IS_ACCOUNT_NON_LOCKED = true;
    private static final boolean IS_CREDENTIALS_NON_EXPIRED = true;
    private static final boolean IS_ENABLED = true;

    private UserMapper() {}
    
    public static User toEntity(UserRequest userRequest) {
        return User.builder()
                .email(userRequest.email())
                .password(userRequest.password())
                .role(Role.USER)
                .isAccountNonExpired(IS_ACCOUNT_NON_EXPIRED)
                .isAccountNonLocked(IS_ACCOUNT_NON_LOCKED)
                .isCredentialsNonExpired(IS_CREDENTIALS_NON_EXPIRED)
                .isEnabled(IS_ENABLED).build();
    }

    public static UserResponse toDto(User user) {
        return UserResponse.builder()
                .idUser(user.getIdUser())
                .email(user.getEmail()).build();
    }
}