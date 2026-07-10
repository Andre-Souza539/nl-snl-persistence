package dev.nerlab.backend.dto.transactions;

import dev.nerlab.backend.model.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionRequestDTO(
        @NotBlank @Size(max = 300) String description,
        @NotBlank BigDecimal amount,
        @NotBlank CategoryType type,
        @NotBlank LocalDate date,
        @NotBlank UUID categoryId,
        @NotBlank UUID familyId
) {
}
