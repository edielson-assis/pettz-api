package br.com.pettz.dtos.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record UserResponse(UUID idUser, String email) {}