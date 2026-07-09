package dev.nerlab.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import dev.nerlab.backend.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.nerlab.backend.model.User;
import dev.nerlab.backend.model.UserStatus;
import dev.nerlab.backend.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(UUID id){
        return repository.findById(id)
            .orElseThrow(()-> new UserNotFoundException(id));
    }

    public User create(User user){
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    public User update(UUID id, User updateUser){
        User existing = repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        existing.setFirstName(updateUser.getFirstName());
        existing.setLastName(updateUser.getLastName());
        existing.setEmail(updateUser.getEmail());
        existing.setStatus(
            updateUser.getStatus() != null 
                ? updateUser.getStatus() 
                : existing.getStatus()
        );
        existing.setUpdatedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public void deactivate(UUID id){

        User existing = findById(id);
        existing.setStatus(UserStatus.INACTIVE);
        update(id, existing);

    }

    public void deleteById(UUID id) {
        User userFound = repository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        repository.delete(userFound);
    }
}
