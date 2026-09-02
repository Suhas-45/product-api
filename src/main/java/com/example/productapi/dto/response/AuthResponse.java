package com.example.productapi.dto.response;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) {
}
