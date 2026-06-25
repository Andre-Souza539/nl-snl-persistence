package dev.nerlab.backend.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.nerlab.backend.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    
}
