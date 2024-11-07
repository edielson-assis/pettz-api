package br.com.pettz.services;

import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.TokenAndRefreshTokenResponse;
import br.com.pettz.dtos.response.TokenResponse;
import br.com.pettz.dtos.response.UserResponse;

public interface UserService {
    
    UserResponse signup(UserSignupRequest userRequest);

    TokenAndRefreshTokenResponse signin(UserSigninRequest user);

    TokenResponse refreshToken(String username, String refreshToken);

    void disableUser(String email);
}