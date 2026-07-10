package dev.nerlab.backend.service;

import dev.nerlab.backend.dto.transactions.TransactionRequestDTO;
import dev.nerlab.backend.exception.CustomRuntimeException;
import dev.nerlab.backend.model.Category;
import dev.nerlab.backend.model.Family;
import dev.nerlab.backend.model.Transaction;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.repository.*;
import dev.nerlab.backend.utils.FamilyMembershipValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final FamilyRepository familyRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final FamilyMembershipValidator membershipValidator;

    @Transactional
    public Transaction create(TransactionRequestDTO dto, UUID userId){
        //validaçoes
        membershipValidator.validate(dto.familyId(), userId);
        membershipValidator.validateCategoryFamily(dto.categoryId(), dto.familyId());

        Family family = familyRepository.findById(dto.familyId())
                .orElseThrow(()-> new CustomRuntimeException("Família não encontrada."));
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new CustomRuntimeException("Usuário não encontrado."));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(()-> new CustomRuntimeException("Categoria não encontrada."));


        Transaction transaction = new Transaction();
        transaction.setFamily(family);
        transaction.setUser(user);
        transaction.setCategory(category);
        transaction.setDescription(dto.description());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setDate(dto.date());
        transaction.setCreatedAt(Instant.now());

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllByFamily(UUID familyId, UUID userId){
        membershipValidator.validate(familyId,userId);
        return transactionRepository.findByFamilyId(familyId);
    }

    @Transactional
    public Transaction update(UUID transactionId, UUID familyId, TransactionRequestDTO dto, UUID userId){
        membershipValidator.validate(familyId,userId);
        membershipValidator.validateCategoryFamily(dto.categoryId(),familyId);

        Transaction transaction = transactionRepository.findByIdAndFamilyId(transactionId, familyId)
                .orElseThrow(()-> new CustomRuntimeException("Transação não encontrada para esta família"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(()-> new CustomRuntimeException("Categoria não encontrada"));

        transaction.setCategory(category);
        transaction.setDescription(dto.description());
        transaction.setAmount(dto.amount());
        transaction.setType(dto.type());
        transaction.setDate(dto.date());
        transaction.setUpdatedAt(Instant.now());

        return transactionRepository.save(transaction);
    }

    @Transactional
    public void delete(UUID transactionId, UUID familyId, UUID userId){
        membershipValidator.validate(familyId,userId);

        Transaction transaction = transactionRepository.findByIdAndFamilyId(transactionId,familyId)
                .orElseThrow(()-> new CustomRuntimeException("Transação não encontrada para esta família"));

        transactionRepository.delete(transaction);
    }

}
