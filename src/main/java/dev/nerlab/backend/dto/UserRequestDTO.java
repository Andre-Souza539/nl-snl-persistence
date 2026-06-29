package dev.nerlab.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @Email @NotBlank
        String email,
        @NotBlank String password
) {
}
