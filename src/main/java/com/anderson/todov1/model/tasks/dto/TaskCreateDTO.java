package com.anderson.todov1.model.tasks.dto;

import com.anderson.todov1.model.tasks.Task;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record TaskCreateDTO(
        @NotBlank(message = "O título é obrigatório.")
        String title,

        @NotBlank(message = "A descrição é obrigatória.")
        String description,

        @NotNull(message = "A data de vencimento é obrigatória.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate expiredDate
) {
    public TaskCreateDTO (Task task) {
        this(task.getTitle(), task.getDescription(), task.getExpiredDate());
    }
}
