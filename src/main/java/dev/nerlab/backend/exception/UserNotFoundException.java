package dev.nerlab.backend.exception;

import java.util.UUID;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(UUID id) {
        super("User Not Found: " + id);
    }
}
