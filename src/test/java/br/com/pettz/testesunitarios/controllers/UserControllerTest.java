package br.com.pettz.testesunitarios.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pettz.PettzApplication;
import br.com.pettz.controllers.UserController;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.mappers.UserMapper;
import br.com.pettz.models.User;
import br.com.pettz.repositories.UserRepository;
import br.com.pettz.security.SecurityConfiguration;
import br.com.pettz.security.TokenService;
import br.com.pettz.services.UserService;

@ContextConfiguration(classes = {PettzApplication.class, SecurityConfiguration.class})
@WebMvcTest(UserController.class)
class UserControllerTest {

    private static final String USER_REGISTER_URI = "/api/v1/auth/register";
    private static final String USER_LOGIN_URI = "/api/v1/auth/login";
    private static final String EMAIL = "teste@email.com";
    private static final String PASSWORD = "123456";
    private static final String INVALIDA_PASSWORD = "12345678";
    private static final String TOKEN_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3Mi";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthenticationManager manager;

    @MockBean
    private UserService service;

    @MockBean
    private UserRepository repository;

    private UserSignupRequest userRequest;

    // @BeforeEach
    // void setup() {
    //     userRequest = new UserSignupRequest(EMAIL, PASSWORD);
    // }

    // @Test
    // @DisplayName("JUnit test - should create a user, return status code 201, and an object of type UserResponse")
    // void shouldCreateAUserAndReturnAnObjectOfTypeUserResponse() throws Exception {

    //     User user = UserMapper.toEntity(userRequest);
    //     given(service.registerUser(any(UserSignupRequest.class))).willReturn(UserMapper.toDto(user));

    //     UserResponse userResponse = service.registerUser(userRequest);

    //     ResultActions response = mockMvc.perform(post(USER_REGISTER_URI).contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(userRequest)));

    //     response.andDo(print()).andExpect(status().isCreated())
    //             .andExpect(jsonPath("$.email", is(userResponse.email())));
    // }

    // @Test
    // @DisplayName("Should perform login, return status code 200, and return a JWT token")
    // void testShouldPerformLoginAndReturnAJwtToken() throws Exception {
          
    //     given(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willReturn(new UsernamePasswordAuthenticationToken(new User(), null));
    //     given(tokenService.generateToken(any(User.class))).willReturn(TOKEN_JWT);
    
    //     ResultActions resposta = mockMvc.perform(post(USER_LOGIN_URI).contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(userRequest)));

    //     resposta.andDo(print()).andExpect(status().isOk())
    //             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(jsonPath("$.token").value(TOKEN_JWT));
    // }

    // @Test
    // @DisplayName("Should return status code 401 when the credentials are invalid")
    // void testShouldReturnStatusCode401WhenTheCredentialsAreInvalid() throws Exception {
          
    //     given(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).willThrow(BadCredentialsException.class);
    
    //     ResultActions resposta = mockMvc.perform(post(USER_LOGIN_URI).contentType(MediaType.APPLICATION_JSON)
    //             .content(objectMapper.writeValueAsString(new UserSignupRequest(EMAIL, INVALIDA_PASSWORD))));

    //     resposta.andDo(print()).andExpect(status().isUnauthorized());
    // }
}