package br.com.pettz.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pettz.controllers.swagger.UserControllerSwagger;
import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.models.User;
import br.com.pettz.security.TokenJWT;
import br.com.pettz.security.TokenService;
import br.com.pettz.services.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/auth", produces = "application/json")
public class UserController implements UserControllerSwagger {

    private final UserService service;
    private final AuthenticationManager manager;
    private final TokenService tokenService;
    
    @Transactional
    @PostMapping(path = "/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest userRequest) {
        var user = service.register(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(user.idUser()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @Transactional
    @PostMapping(path = "/login")
    public ResponseEntity<TokenJWT> login(@RequestBody @Valid UserRequest user) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(user.email(), user.password());
        var authentication = manager.authenticate(authenticationToken);
        
        var tokenJWT = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWT(tokenJWT));
    }
}