package com.anderson.todov1.model.tasks.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record TaskResponseDTO(
        UUID id,
        String title,
        String description,
        Boolean status,
        LocalDate expiredDate,
        LocalDateTime createdAt,
        LocalDateTime deletedAt
) {
}
