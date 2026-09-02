package com.example.productapi.dto.response;

import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String productName,
        String createdBy,
        LocalDateTime createdOn,
        String modifiedBy,
        LocalDateTime modifiedOn
) {
}