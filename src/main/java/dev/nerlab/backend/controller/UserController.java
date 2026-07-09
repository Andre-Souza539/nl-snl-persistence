package dev.nerlab.backend.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import dev.nerlab.backend.dto.user.UserRequestDTO;
import dev.nerlab.backend.dto.user.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.UserService;
import lombok.AllArgsConstructor;





@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<User> users = service.findAll();
        List<UserResponseDTO> resposne = users.stream()
                .map(UserResponseDTO::new)
                .toList();
        return ResponseEntity.ok(resposne);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getByid(@PathVariable UUID id) {
        User user = service.findById(id);
        return ResponseEntity.ok(new UserResponseDTO(user));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        User createdUser = service.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new UserResponseDTO(createdUser));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable UUID id, @Valid @RequestBody UserRequestDTO dto) {

        User userToUpdate = service.findById(id);
        userToUpdate.setFirstName(dto.firstName());
        userToUpdate.setLastName(dto.lastName());
        userToUpdate.setEmail(dto.email());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        User updatedUser = service.update(id, userToUpdate);

        return ResponseEntity.ok(new UserResponseDTO(updatedUser));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable UUID id){
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletebyId(@PathVariable UUID id){
        User existingUser = service.findById(id);
        if(existingUser != null){
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
