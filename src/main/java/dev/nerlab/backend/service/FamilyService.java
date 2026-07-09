package dev.nerlab.backend.service;

import dev.nerlab.backend.dto.family.FamilyRequestDTO;
import dev.nerlab.backend.dto.family.FamilyResponseDTO;
import dev.nerlab.backend.exception.CustomRuntimeException;
import dev.nerlab.backend.exception.FamilyNotFoundException;
import dev.nerlab.backend.exception.ResourceNotFoundException;
import dev.nerlab.backend.exception.UserNotFoundException;
import dev.nerlab.backend.model.Family;
import dev.nerlab.backend.model.FamilyMember;
import dev.nerlab.backend.model.FamilyMemberId;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.repository.FamilyMemberRepository;
import dev.nerlab.backend.repository.FamilyRepository;
import dev.nerlab.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final UserRepository userRepository;

    @Transactional
    public Family createFamily(FamilyRequestDTO dto, UUID creatorUserId){
        // 1. valida se o usuário criador realmente existe
        User creator = userRepository.findById(creatorUserId).orElseThrow(
                ()-> new UserNotFoundException(creatorUserId));

        // criar e salvar a familia principal
        Family family = new Family();
        family.setName(dto.name());
        Family savedFamily = familyRepository.save(family);

        // cria chave composta (family ID + User ID)
        FamilyMemberId memberId =
                new FamilyMemberId(savedFamily.getId(), creator.getId());

        FamilyMember ownerMember = new FamilyMember();
        ownerMember.setId(memberId);
        ownerMember.setFamily(savedFamily);
        ownerMember.setUser(creator);
        ownerMember.setRole("OWNER");

        familyMemberRepository.save(ownerMember);

        return savedFamily;
    }

    public List<FamilyResponseDTO> getUserFamilies(UUID userId){
        userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(userId));

        List<FamilyMember> memberships = familyMemberRepository.findByUserId(userId);

        return memberships.stream()
                .map(member -> new FamilyResponseDTO(member.getFamily(), member.getRole()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUserFamilyById(UUID familyId, UUID userId) {
        // 1. Busca o vínculo específico deste usuário com esta família
        FamilyMemberId memberId = new FamilyMemberId(familyId, userId);
        FamilyMember requestingUserLink = familyMemberRepository.findById(memberId)
                .orElseThrow(() -> new FamilyNotFoundException(familyId));

        // 2. Regra de Negócio: Somente o Dono pode deletar a Área de Trabalho
        if (!"OWNER".equals(requestingUserLink.getRole())) {
            // Em uma evolução futura, podemos criar uma exceção customizada de "ForbiddenException" (403)
            throw new RuntimeException("Acesso negado: Apenas o OWNER pode deletar esta família.");
        }

        // 3. Resolve o conflito de Chave Estrangeira: Deleta os "filhos" (Membros) primeiro
        List<FamilyMember> allMembers = familyMemberRepository.findByFamilyId(familyId);
        familyMemberRepository.deleteAll(allMembers);

        // 4. Agora sim podemos deletar o "pai" (Família) com segurança
        familyRepository.deleteById(familyId);
    }

    @Transactional
    public Family updateFamily(UUID familyId, UUID userId, FamilyRequestDTO dto){
        FamilyMemberId memberId = new FamilyMemberId(familyId,userId);
        FamilyMember requestingUserLink = familyMemberRepository.findById(memberId)
                .orElseThrow(()-> new CustomRuntimeException("Familia não encontrada"));

        if(!"OWNER".equals(requestingUserLink.getRole())){
            throw new CustomRuntimeException("Acesso negado: Apenas Owner pode atualizar");
        }

        Family family = familyRepository.findById(familyId)
                .orElseThrow(()-> new FamilyNotFoundException(familyId));

        family.setName(dto.name());

        return familyRepository.save(family);

    }

    public FamilyResponseDTO findByid(UUID familyId, UUID userId) {
        FamilyMemberId memberId = new FamilyMemberId(familyId, userId);
        FamilyMember member = familyMemberRepository.findById(memberId)
                .orElseThrow(()-> new FamilyNotFoundException(familyId));

        return new FamilyResponseDTO(member.getFamily(),member.getRole());
    }
}
