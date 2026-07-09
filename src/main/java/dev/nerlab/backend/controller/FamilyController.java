package dev.nerlab.backend.controller;

import dev.nerlab.backend.dto.family.FamilyRequestDTO;
import dev.nerlab.backend.dto.family.FamilyResponseDTO;
import dev.nerlab.backend.dto.user.UserResponseDTO;
import dev.nerlab.backend.model.Family;
import dev.nerlab.backend.service.FamilyService;
import dev.nerlab.backend.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestHeader("X-User-Id") UUID userid,
            @Valid @RequestBody FamilyRequestDTO dto) {
        Family savedFamily = familyService.createFamily(dto, userid);
        FamilyResponseDTO response = new FamilyResponseDTO(savedFamily, "OWNER");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<FamilyResponseDTO>> getUserFamilies(
            @RequestHeader("X-User-Id") UUID userId
    ) {
        List<FamilyResponseDTO> families = familyService.getUserFamilies(userId);
        return ResponseEntity.ok(families);
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<FamilyResponseDTO> getUserFamilyById(
            @PathVariable UUID familyId, @RequestHeader("X-User-Id") UUID userId){
        FamilyResponseDTO familyFound = familyService.findByid(familyId, userId);
        return ResponseEntity.ok(familyFound);
    }

    @PutMapping("/{familyId}")
    public ResponseEntity<FamilyResponseDTO> updateFamily(
            @PathVariable("familyId") UUID familyId,
            @RequestHeader("X-User-Id") UUID userId,
            @Valid @RequestBody FamilyRequestDTO dto
    ) {

        Family updatedFamily = familyService.updateFamily(familyId,userId,dto);
        return ResponseEntity.ok(new FamilyResponseDTO(updatedFamily, "OWNER"));
    }

    @DeleteMapping("/{familyId}")
    public ResponseEntity<Void> deleteFamily(
            @RequestHeader("X-User-Id") UUID userId,
            @PathVariable("familyId") UUID familyId) {
        familyService.deleteUserFamilyById(familyId,userId);
        return ResponseEntity.noContent().build();
    }

}
