package br.com.pettz.testesunitarios.mocks;

import java.util.UUID;

import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.models.enums.Role;

public class MockUser {

    private static final UUID USER_ID = UUID.fromString("99ac1044-0c6e-4950-bfd4-76a2f3e074ae");
    
    public User user() {
        User user = new User();
        user.setIdUser(USER_ID);
        user.setFullName("Test auth");
        user.setEmail("teste@email.com");
        user.setPassword("91e2532173dc95ef503ed5ed39f7822f576a93b7c5ae41ef52b2467bd0234f089bbfd3f3f79ed7ba");
        user.setRole(Role.USER);
        return user;
    }

    public UserSignupRequest userSignup() {
        return UserSignupRequest.builder()
            .fullName("Test auth")
            .email("teste@email.com")
            .password("1234567").build();
    }

    public UserSigninRequest userSignin() {
        return UserSigninRequest.builder()
            .email("teste@email.com")
            .password("1234567").build();
    }

    public UserResponse userResponse() {
        return UserResponse.builder()
            .userId(USER_ID)
            .fullName("Test auth")
            .email("teste@email.com").build();
    }
}