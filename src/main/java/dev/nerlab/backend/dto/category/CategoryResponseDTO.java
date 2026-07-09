package dev.nerlab.backend.dto.category;

import dev.nerlab.backend.model.Category;
import dev.nerlab.backend.model.CategoryType;

import java.time.Instant;
import java.util.UUID;

public record CategoryResponseDTO(
        UUID id,
        String name,
        CategoryType type,
        UUID familyId,
        Instant createdAt
) {
    public CategoryResponseDTO(Category category){
        this(
                category.getId(),
                category.getName(),
                category.getType(),
                category.getFamily().getId(),
                category.getCreatedAt()
        );
    }
}
