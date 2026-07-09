package dev.nerlab.backend.repository;

import dev.nerlab.backend.model.FamilyMember;
import dev.nerlab.backend.model.FamilyMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FamilyMemberRepository extends JpaRepository<FamilyMember, FamilyMemberId> {

    List<FamilyMember> findByUserId(UUID userId);
    List<FamilyMember> findByFamilyId(UUID familyId);
}
