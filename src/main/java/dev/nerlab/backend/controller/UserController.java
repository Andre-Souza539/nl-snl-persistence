package dev.nerlab.backend.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.UserService;
import lombok.AllArgsConstructor;





@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    
    private final UserService service;

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }
    
    @GetMapping("/{id}")
    public User getMethodName(@PathVariable String pathId) {
        UUID id = UUID.fromString(pathId);
        return service.findById(id);
    }
    
    @PostMapping()
    public User postMethodName(@RequestBody User user) {        
        return service.create(user);
    }
    
    @PutMapping("/{id}")
    public User putMethodName(@PathVariable String pathId, @RequestBody User user) {
        UUID id = UUID.fromString(pathId);
        return service.update(id, user);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Object> deactivate(@PathVariable String pathId){
        UUID id = UUID.fromString(pathId);
        service.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
