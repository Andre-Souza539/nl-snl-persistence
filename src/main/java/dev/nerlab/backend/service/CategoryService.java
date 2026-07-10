package dev.nerlab.backend.service;

import dev.nerlab.backend.dto.category.CategoryRequestDTO;
import dev.nerlab.backend.exception.CustomRuntimeException;
import dev.nerlab.backend.model.Category;
import dev.nerlab.backend.model.Family;
import dev.nerlab.backend.model.FamilyMemberId;
import dev.nerlab.backend.repository.CategoryRepository;
import dev.nerlab.backend.repository.FamilyMemberRepository;
import dev.nerlab.backend.repository.FamilyRepository;
import dev.nerlab.backend.utils.FamilyMembershipValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final FamilyRepository familyRepository;
    private final FamilyMembershipValidator membershipValidator;
    
    @Transactional
    public Category create(CategoryRequestDTO dto, UUID userId){
        membershipValidator.validate(dto.familyId(), userId);
        Family family = familyRepository.findById(dto.familyId())
                .orElseThrow(()-> new CustomRuntimeException("Família não encontrada"));

        Category category = new Category();
        category.setName(dto.name());
        category.setType(dto.type());
        category.setFamily(family);
        category.setCreatedAt(Instant.now());

        return categoryRepository.save(category);
    }

    public List<Category> findAllByFamily(UUID familyId, UUID userId){
        membershipValidator.validate(familyId,userId);
        return categoryRepository.findByFamilyId(familyId);
    }

    @Transactional
    public void delete(UUID categoryId, UUID familyId, UUID userId){
        membershipValidator.validate(familyId,userId);

        Category category = categoryRepository.findByIdAndFamilyId(categoryId,familyId)
                .orElseThrow(()-> new CustomRuntimeException("Categoria não Encontrada para esta família."));

        categoryRepository.delete(category);
    }


}
