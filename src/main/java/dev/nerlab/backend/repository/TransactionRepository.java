package dev.nerlab.backend.repository;

import dev.nerlab.backend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findByFamilyId(UUID familyId);
    Optional<Transaction> findByIdAndFamilyId(UUID id, UUID familyId);

    List<Transaction> findByFamilyIdAndCategoryId(UUID familyId, UUID categoryId);
    List<Transaction> findByFamilyIdAndDateBetween(UUID familyId, LocalDate startDate, LocalDate endDate);
}
