package br.com.pettz.controller.swagger;

import org.springframework.http.ResponseEntity;

import br.com.pettz.dtos.request.UserRequest;
import br.com.pettz.dtos.response.UserResponse;
import br.com.pettz.security.TokenJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Authentication management APIs")
public interface UserControllerSwagger {
    
    @Operation(
      summary = "Create a user",
      description = "Create a user. The response, if successful, is a JSON with information about created user.",
      tags = {"Authentication"}
  )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful create user", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request.", content = @Content)
    })
    ResponseEntity<UserResponse> register(UserRequest userRequest);

    @Operation(
      summary = "Perform authentication",
      description = "Endpoint to user perform authentication. The response, if successful, is a JSON with JWT.",
      tags = {"Authentication"}
  )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful user authenticatio.", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TokenJWT.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request - Something is wrong with the request", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid email or password", content = @Content)
    })
    ResponseEntity<TokenJWT> login(UserRequest user);
}