package dev.nerlab.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyMemberId {
    @Column(name = "family_id")
    private UUID familyId;
    @Column(name = "user_id")
    private UUID userId;
}
