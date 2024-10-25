package br.com.pettz.integrationstestes.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.TokenAndRefreshTokenResponse;
import br.com.pettz.dtos.response.TokenResponse;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.integrationstestes.configs.AbstractIntegrationTest;
import br.com.pettz.integrationstestes.configs.TestConfig;
import br.com.pettz.testesunitarios.mocks.MockUser;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
	private static ObjectMapper objectMapper;
    private static UserSignupRequest userSignup;
	private static TokenAndRefreshTokenResponse token;
    private static MockUser input;
    private static final String BASE_PATH = "/api/v1/auth";
    private static final String USERNAME = "teste@email.com";

    @BeforeAll
	static void setup() {
		objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		input = new MockUser();
        userSignup = input.userSignup();
	}

    @Test
	@Order(0)
    @DisplayName("Should signup and return a UserResponse")
	void testShouldSignupAndReturnUserResponse() throws JsonMappingException, JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfig.HEADER_PARAM_ORIGIN, TestConfig.ORIGIN_3000)
                .setContentType(TestConfig.APPLICATION_JSON)
                .setPort(TestConfig.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();
		
        var content = given().spec(specification)
                .basePath(BASE_PATH.concat("/signup"))
                .body(userSignup)
                .when()
                .post()
				.then()
                .statusCode(201)
                .extract()
                .body()
                .asString();

        UserResponse user = objectMapper.readValue(content, UserResponse.class);
		
		assertNotNull(user);
		assertNotNull(user);

        assertEquals("Test auth", user.fullName());
        assertEquals("teste@email.com", user.email());
	}
	
	@Test
	@Order(1)
	@DisplayName("Should perform login and return a JWT token and a refresh token")
    void testShouldPerformLoginAndReturnAJwtTokenAndARefreshToken() throws JsonMappingException, JsonProcessingException {
		UserSigninRequest userSignin = input.userSignin();
		
		token = given().spec(specification)
				.basePath(BASE_PATH.concat("/signin"))
				.body(userSignin)
                .when()
				.post()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(TokenAndRefreshTokenResponse.class);
		
		assertNotNull(token.idUser());
		assertNotNull(token.fullName());
		assertNotNull(token.accessToken());
		assertNotNull(token.refreshToken());
	}
	
	@Test
	@Order(2)
    @DisplayName("Should refresh a JWT token")
	void testShouldRefreshAJWTToken() throws JsonMappingException, JsonProcessingException {
		var newTokenResponse = given().spec(specification)
				.basePath(BASE_PATH.concat("/refresh"))
                .pathParam("username", USERNAME)
                .header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.refreshToken())
				.when()
                .get("{username}")
				.then()
                .statusCode(200)
				.extract()
                .body()
                .as(TokenResponse.class);
		
		assertNotNull(newTokenResponse.accessToken());
	}

    @Test
    @Order(3)
    @DisplayName("When delete user then return no content")
    void testWhenDeleteUserThenReturnNoContent() throws JsonMappingException, JsonProcessingException {
        given().spec(specification)
            .basePath(BASE_PATH.concat("/delete"))
            .pathParam("email", USERNAME)
            .header(TestConfig.HEADER_PARAM_AUTHORIZATION, "Bearer " + token.refreshToken())
            .when()
            .delete("{email}")
            .then()
            .statusCode(204);
    }
}