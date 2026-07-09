package dev.nerlab.backend.dto.category;

import dev.nerlab.backend.model.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CategoryRequestDTO(
        @NotBlank String name,
        @NotNull CategoryType type,
        @NotNull UUID familyId
) {}
