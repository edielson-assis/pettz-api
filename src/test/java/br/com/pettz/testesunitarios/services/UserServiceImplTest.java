package br.com.pettz.testesunitarios.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.repositories.UserRepository;
import br.com.pettz.services.exceptions.ValidationException;
import br.com.pettz.services.impl.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    
    private static final String EMAIL = "teste@email.com";
    private static final String PASSWORD = "123456";

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRequest userRequest;

    @InjectMocks
    private UserServiceImpl service;

    private User user;

    @BeforeEach
    void setup() {
        user = User.builder()
                .email(EMAIL)
                .password(PASSWORD).build();
    }

    @Test
    @DisplayName("When registering a user, it should return an object of type User")
    void testWhenRegisteringAUserItShouldReturnAnObjectOfTypeUser() {

        given(repository.save(user)).willReturn(user);

        UserResponse savedUser = service.register(userRequest);

        assertNotNull(savedUser);
        assertEquals(EMAIL, savedUser.email());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should return a ValidationException if the provided email is already registered")
    void testShouldReturnAValidationExceptionIfTheProvidedEmailIsAlreadyRegistered() {

        given(repository.existsByEmail(anyString())).willReturn(true);

        assertThrows(ValidationException.class, () -> service.register(new UserRequest(EMAIL, PASSWORD)));

        verify(repository, never()).save(any(User.class));
    }
}