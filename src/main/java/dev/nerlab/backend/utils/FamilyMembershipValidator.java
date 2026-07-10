package dev.nerlab.backend.utils;

import dev.nerlab.backend.exception.CustomRuntimeException;
import dev.nerlab.backend.model.Category;
import dev.nerlab.backend.model.FamilyMemberId;
import dev.nerlab.backend.repository.CategoryRepository;
import dev.nerlab.backend.repository.FamilyMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FamilyMembershipValidator {

    private final CategoryRepository categoryRepository;
    private final FamilyMemberRepository familyMemberRepository;

    public void validate(UUID familyId, UUID userId) {
        boolean isMember = familyMemberRepository.existsById(new FamilyMemberId(familyId, userId));
        if (!isMember) {
            throw new CustomRuntimeException("Acesso Negado: Você não pertence a esta família");
        }
    }

    public void validateCategoryFamily(UUID categoryId, UUID familyId) {
        Category category = categoryRepository.findByIdAndFamilyId(categoryId,familyId)
                .orElseThrow(()->new CustomRuntimeException("Acesso Negado: Você não pertence a esta família"));
    }

}
