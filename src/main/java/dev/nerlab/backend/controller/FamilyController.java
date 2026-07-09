package dev.nerlab.backend.controller;

import dev.nerlab.backend.dto.family.FamilyRequestDTO;
import dev.nerlab.backend.dto.family.FamilyResponseDTO;
import dev.nerlab.backend.dto.user.UserResponseDTO;
import dev.nerlab.backend.model.Family;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.FamilyService;
import dev.nerlab.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/families")
@RequiredArgsConstructor
public class FamilyController {

    private final FamilyService familyService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<FamilyResponseDTO> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody FamilyRequestDTO dto) {
        Family savedFamily = familyService.createFamily(dto, user.getId());
        FamilyResponseDTO response = new FamilyResponseDTO(savedFamily, "OWNER");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FamilyResponseDTO>> getUserFamilies(
            @AuthenticationPrincipal User user
    ) {
        List<FamilyResponseDTO> families = familyService.getUserFamilies(user.getId());
        return ResponseEntity.ok(families);
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<FamilyResponseDTO> getUserFamilyById(
            @PathVariable UUID familyId, @AuthenticationPrincipal User user){
        FamilyResponseDTO familyFound = familyService.findByid(familyId, user.getId());
        return ResponseEntity.ok(familyFound);
    }

    @PutMapping("/{familyId}")
    public ResponseEntity<FamilyResponseDTO> updateFamily(
            @PathVariable("familyId") UUID familyId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody FamilyRequestDTO dto
    ) {

        Family updatedFamily = familyService.updateFamily(familyId,user.getId(),dto);
        return ResponseEntity.ok(new FamilyResponseDTO(updatedFamily, "OWNER"));
    }

    @DeleteMapping("/{familyId}")
    public ResponseEntity<Void> deleteFamily(
            @AuthenticationPrincipal User user,
            @PathVariable("familyId") UUID familyId) {
        familyService.deleteUserFamilyById(familyId,user.getId());
        return ResponseEntity.noContent().build();
    }

}
