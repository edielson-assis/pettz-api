package br.com.pettz.services.impl;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.TokenAndRefreshTokenResponse;
import br.com.pettz.dtos.response.TokenResponse;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.mappers.UserMapper;
import br.com.pettz.models.User;
import br.com.pettz.repositories.UserRepository;
import br.com.pettz.security.TokenService;
import br.com.pettz.services.UserService;
import br.com.pettz.services.exceptions.ValidationException;
import br.com.pettz.utils.component.AuthenticatedUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Override
    public UserResponse signup(UserSignupRequest userRequest) {
        User user = UserMapper.toEntity(userRequest);
        validateEmailNotExists(user);
        encryptPassword(user);
        log.info("Registering a new User");
        return UserMapper.toDto(repository.save(user));
    }

    @Override
    public TokenAndRefreshTokenResponse signin(UserSigninRequest user) {
		return authenticateUser(user);
	}

    @Override
    public TokenResponse refreshToken(String username, String refreshToken) {
        return tokenService.refreshToken(refreshToken, username);
	}

    @Override
    public void disableUser(String email) {
		var user = AuthenticatedUser.getCurrentUser();
		if (!(user.getEmail().equals(email) || hasPermissionToDeleteUser(user))) {
            throw new AccessDeniedException("You do not have permission to delete users");
        }
		findUserByEmail(email);
		log.info("Disabling user with email: {}", email);
		repository.disableUser(email);
	}
    
    private User findUserByEmail(String email) {
        log.info("Verifying the user's email: {}", email);
        return repository.findByEmail(email).orElseThrow(() -> {
            log.error("Username not found: {}", email);
            return new UsernameNotFoundException("Username not found: " + email);
        });    
    }

	private synchronized void validateEmailNotExists(User user) {
		log.info("Verifying the user's email: {}", user.getEmail());
        var exists = repository.existsByEmail(user.getEmail().toLowerCase());
        if (exists) {
            log.error("Email already exists: {}", user.getEmail());
            throw new ValidationException("Email already exists");
        }
    }

    private void encryptPassword(User user) {
		log.info("Encrypting password");
        user.setPassword(encoder.encode(user.getPassword()));
    }

    private TokenAndRefreshTokenResponse authenticateUser(UserSigninRequest data) {
		var username = data.email();
		try {
			log.debug("Authenticating user with email: {}", username);
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, data.password()));
			log.debug("Authentication successful for user: {}", username);
			var user = findUserByEmail(username);
			log.info("Generating access and refresh token for user: {}", username);
			return tokenService.createAccessTokenRefreshToken(user.getUserId(), user.getFullName(), user.getUsername(), user.getRoles());
		} catch (Exception e) {
			log.error("Invalid username or password for user: {}", username);
			throw new BadCredentialsException("Invalid username or password");
		}
	}

	private boolean hasPermissionToDeleteUser(User user) {
        return user.getRole().name().equals("ADMIN");
    }
}