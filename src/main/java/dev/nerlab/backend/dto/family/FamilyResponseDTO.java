package dev.nerlab.backend.dto.family;

import dev.nerlab.backend.model.Family;

import java.util.UUID;

public record FamilyResponseDTO(
        UUID id,
        String name,
        String role
) {
    public FamilyResponseDTO(Family family, String role){
        this(
                family.getId(),
                family.getName(),
                role);
    }

    public FamilyResponseDTO(Family family){
        this(
                family.getId(),
                family.getName(),
                null
        );
    }
}
