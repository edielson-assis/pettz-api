package br.com.pettz.testesunitarios.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.TokenAndRefreshTokenResponse;
import br.com.pettz.dtos.response.TokenResponse;
import br.com.pettz.models.User;
import br.com.pettz.repositories.UserRepository;
import br.com.pettz.security.TokenService;
import br.com.pettz.services.exceptions.ValidationException;
import br.com.pettz.services.impl.UserServiceImpl;
import br.com.pettz.testesunitarios.mocks.MockUser;

@TestInstance(Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;
	
    @Mock
    private TokenService tokenService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl service;

    private MockUser input;
    private User user;
    private UserSigninRequest userSigninRequest;
    private UserSignupRequest userSignupRequest;

    private static final UUID USER_ID = UUID.fromString("99ac1044-0c6e-4950-bfd4-76a2f3e074ae");
    private static final Integer NUMBER_ONE = 1;
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USER_EMAIL = "teste@email.com";
    private static final String WRONG_USER_EMAIL = "teste@example.com";
    private static final String FULL_NAME = "Test auth";

    @BeforeEach
    void setup() {
        input = new MockUser();
        user = input.user();
        userSignupRequest = input.userSignup();
        userSigninRequest = input.userSignin();
    }

    @Test
    @DisplayName("Should signup and return a UserResponse")
	void testShouldSignupAndReturnUserResponse() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(user);

        var savedUser = service.signup(userSignupRequest);

        assertNotNull(savedUser);
        assertNotNull(savedUser.userId());
        assertNotNull(savedUser.fullName());
		assertNotNull(savedUser.email());

        assertEquals(USER_ID, savedUser.userId());
        assertEquals("Test auth", savedUser.fullName());
        assertEquals("teste@email.com", savedUser.email());
        
        verify(repository, times(NUMBER_ONE)).save(any(User.class));
    }

    @Test
    @DisplayName("Should return a ValidationException if the provided email is already registered")
    void testShouldReturnAValidationExceptionIfTheProvidedEmailIsAlreadyRegistered() {
        when(repository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(ValidationException.class, () -> service.signup(userSignupRequest));

        verify(repository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Should perform login and return a JWT token and a refresh token")
    void testShouldPerformLoginAndReturnAJwtTokenAndARefreshToken() {
        TokenAndRefreshTokenResponse tAndRefreshTokenResponse = new TokenAndRefreshTokenResponse(USER_ID, FULL_NAME, ACCESS_TOKEN, REFRESH_TOKEN);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(tokenService.createAccessTokenRefreshToken(user.getUserId(), user.getFullName(), user.getUsername(), user.getRoles())).thenReturn(tAndRefreshTokenResponse);

        var response = service.signin(userSigninRequest);

        assertNotNull(response);

        assertEquals(ACCESS_TOKEN, response.accessToken());
        assertEquals(REFRESH_TOKEN, response.refreshToken());

        verify(repository, times(NUMBER_ONE)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Should return an BadCredentialsException if the user is not found")
    void testShouldReturnAnBadCredentialsExceptionIfTheUserIsNotFound() {
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());
    
        assertThrows(BadCredentialsException.class, () -> service.signin(userSigninRequest));

        verify(repository, times(NUMBER_ONE)).findByEmail(anyString());
    }

    @Test
    @DisplayName("Should refresh a JWT token")
	void testShouldRefreshAJWTToken() {
        when(tokenService.refreshToken(REFRESH_TOKEN, userSigninRequest.email())).thenReturn(new TokenResponse(ACCESS_TOKEN));

        var response = service.refreshToken(userSigninRequest.email(), REFRESH_TOKEN);

        assertNotNull(response);
        assertEquals(ACCESS_TOKEN, response.accessToken());
    }

    @Test
    @DisplayName("When delete user then return no content")
    void testWhenDeleteUserThenReturnNoContent() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        doNothing().when(repository).disableUser(user.getEmail());

        SecurityContextHolder.setContext(securityContext);

        service.disableUser(USER_EMAIL);
        
        verify(repository, times(NUMBER_ONE)).disableUser(anyString());
    }

    @Test
    @DisplayName("When delete user then throw AccessDeniedException")
    void testWhenDeleteUserThenThrowAccessDeniedException() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        SecurityContextHolder.setContext(securityContext);
        
        assertThrows(AccessDeniedException.class, () -> service.disableUser(WRONG_USER_EMAIL));
        
        verify(repository, never()).disableUser(anyString());
    }
}