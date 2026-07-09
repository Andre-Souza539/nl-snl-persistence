package dev.nerlab.backend.dto.family;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FamilyRequestDTO(
        @NotBlank(message = "Family name cannot be empty")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name
) {
}
