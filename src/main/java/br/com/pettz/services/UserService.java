package br.com.pettz.services;

import org.springframework.security.core.userdetails.UserDetails;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;

public interface UserService {
    
    UserResponse register(UserRequest userRequest);

    UserDetails loadUserByUsername(String email);
}