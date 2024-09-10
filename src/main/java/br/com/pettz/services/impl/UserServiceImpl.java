package br.com.pettz.services.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.mappers.UserMapper;
import br.com.pettz.models.User;
import br.com.pettz.repositories.UserRepository;
import br.com.pettz.services.UserService;
import br.com.pettz.services.exceptions.ObjectNotFoundException;
import br.com.pettz.services.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse register(UserRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        validateEmailNotExists(user);
        encryptPassword(user);
        log.info("Registering a new User");
        return UserMapper.toDto(repository.save(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            log.info("Verifying the user's email: {}", email);
            return repository.findByEmail(email);   
        } catch (UsernameNotFoundException e) {
            log.error("User not found.", e.getMessage());
            throw new ObjectNotFoundException("User not found");
        }    
    }
    
    private synchronized void validateEmailNotExists(User user) {
        boolean existeEmail = repository.existsByEmail(user.getEmail());
        if (existeEmail) {
            log.error("Email already exists: {}", user.getEmail());
            throw new ValidationException("Email already exists");
        }
    }

    private void encryptPassword(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
    }
}