package dev.nerlab.backend.controller;

import dev.nerlab.backend.dto.category.CategoryRequestDTO;
import dev.nerlab.backend.dto.category.CategoryResponseDTO;
import dev.nerlab.backend.model.Category;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/families/{familyId}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(
            @PathVariable UUID familyId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CategoryRequestDTO dto
    ) {
        if (!familyId.equals(dto.familyId())) {
            return ResponseEntity.badRequest().build();
        }
        Category category = categoryService.create(dto, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new CategoryResponseDTO(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> listAll(
            @PathVariable UUID familyId,
            @AuthenticationPrincipal User user
    ) {
        List<Category> categories = categoryService.findAllByFamily(familyId, user.getId());
        List<CategoryResponseDTO> response = categories.stream()
                .map(CategoryResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{categoryId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID familyId,
            @PathVariable UUID categoryId,
            @AuthenticationPrincipal User user
    ){
        categoryService.delete(categoryId, familyId, user.getId());
        return ResponseEntity.noContent().build();
    }

}
