package dev.nerlab.backend.controller;

import dev.nerlab.backend.dto.transactions.TransactionRequestDTO;
import dev.nerlab.backend.dto.transactions.TransactionResponseDTO;
import dev.nerlab.backend.model.Transaction;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/families/{familyId}/transactions")
@RequiredArgsConstructor
public class TransactionControler {

    private final TransactionService transactionService;


    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(
            @PathVariable UUID familyId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransactionRequestDTO dto){

        if(!familyId.equals(dto.familyId())){
            return ResponseEntity.badRequest().build();
        }

        Transaction transaction = transactionService.create(dto, user.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponseDTO(transaction));
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> listAll(
            @PathVariable UUID familyId,
            @AuthenticationPrincipal User user
    ){
        List<Transaction> transactions = transactionService.findAllByFamily(familyId, user.getId());
        List<TransactionResponseDTO> response = transactions.stream()
                .map(TransactionResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionResponseDTO> update(
            @PathVariable UUID familyId,
            @PathVariable UUID transactionId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody TransactionRequestDTO dto
    ){
        if(!familyId.equals(dto.familyId())){
            return ResponseEntity.badRequest().build();
        }

        Transaction transaction = transactionService.update(transactionId, familyId, dto, user.getId());

        return ResponseEntity.ok(new TransactionResponseDTO(transaction));
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID familyId,
            @PathVariable UUID transactionId,
            @AuthenticationPrincipal User user
    ){
        transactionService.delete(transactionId, familyId, user.getId());
        return ResponseEntity.noContent().build();
    }

}
