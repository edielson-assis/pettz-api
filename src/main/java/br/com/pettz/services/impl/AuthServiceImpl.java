package br.com.pettz.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.pettz.repositories.UserRepository;
import br.com.pettz.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String email) {
        log.info("Verifying the user's email: {}", email);
        return repository.findByEmail(email).orElseThrow(() -> {
            log.error("Username not found: {}", email);
            return new UsernameNotFoundException("Username not found");
        });    
    }
}