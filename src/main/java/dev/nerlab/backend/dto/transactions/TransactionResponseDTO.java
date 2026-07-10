package dev.nerlab.backend.dto.transactions;

import dev.nerlab.backend.model.CategoryType;
import dev.nerlab.backend.model.Transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record TransactionResponseDTO(
        UUID id,
        UUID familyId,
        UUID userId,
        UUID categoryId,
        String description,
        BigDecimal amount,
        CategoryType type,
        LocalDate date,
        Instant createdAt,
        Instant updatedAt
        ) {
    public TransactionResponseDTO(Transaction transaction){
        this(
                transaction.getId(),
                transaction.getFamily().getId(),
                transaction.getUser().getId(),
                transaction.getCategory().getId(),
                transaction.getDescription(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getCreatedAt(),
                transaction.getUpdatedAt()
        );
    }
}
