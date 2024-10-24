package br.com.pettz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pettz.controllers.swagger.UserControllerSwagger;
import br.com.pettz.dtos.request.UserSigninRequest;
import br.com.pettz.dtos.request.UserSignupRequest;
import br.com.pettz.dtos.response.TokenAndRefreshTokenResponse;
import br.com.pettz.dtos.response.TokenResponse;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
public class UserController implements UserControllerSwagger {

    private final UserService service;;
    
    @Transactional
    @PostMapping(path = "/signup")
	@Override
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserSignupRequest userRequest) {
        var user = service.signup(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
	
    @Transactional
	@PostMapping(path = "/signin")	
	@Override
    public ResponseEntity<TokenAndRefreshTokenResponse> signin(@Valid @RequestBody UserSigninRequest userRequest) {
		var token = service.signin(userRequest);
		return ResponseEntity.ok(token);
	}
	
	@GetMapping(path = "/refresh/{username}")
    @Override
	public ResponseEntity<TokenResponse> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        var token = service.refreshToken(username, refreshToken);
		return ResponseEntity.ok(token);
	}

	@Transactional
	@DeleteMapping(path = "/delete/{email}")
	@Override
	public ResponseEntity<Void> deleteUser(@PathVariable(value = "email") String email) {
		service.disableUser(email);
		return ResponseEntity.noContent().build();
	}
}