package dev.nerlab.backend.repository;

import dev.nerlab.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    List<Category> findByFamilyId(UUID familyId);
    Optional<Category> findByIdAndFamilyId(UUID id, UUID familyId);

}
