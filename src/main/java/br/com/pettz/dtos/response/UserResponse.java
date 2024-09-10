package br.com.pettz.dtos.response;

import lombok.Builder;

@Builder
public record UserResponse(String email) {}