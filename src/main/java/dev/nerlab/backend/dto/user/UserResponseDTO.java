package dev.nerlab.backend.dto.user;

import dev.nerlab.backend.model.User;

import java.util.UUID;

public record UserResponseDTO(
        UUID userId,
        String firstName,
        String lastName,
        String email,
        String status
) {
    public UserResponseDTO(User user){
        this(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getStatus() != null ? user.getStatus().name() : null
        );
    }

}
