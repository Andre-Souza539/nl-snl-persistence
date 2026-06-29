package dev.nerlab.backend.exception;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends Exception {
    public String message;
}
