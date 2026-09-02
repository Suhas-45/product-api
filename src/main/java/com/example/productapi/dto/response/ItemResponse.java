package com.example.productapi.dto.response;

public record ItemResponse(
        Long id,
        Long productId,
        Integer quantity
) {
}
