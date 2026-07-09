package dev.nerlab.backend.exception;

import java.util.UUID;

public class FamilyNotFoundException extends RuntimeException {
    public FamilyNotFoundException(UUID familyId) {
        super("Family not Found for ID: " + familyId);
    }
}
