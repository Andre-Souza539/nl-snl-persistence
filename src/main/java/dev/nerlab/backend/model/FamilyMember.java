package dev.nerlab.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "tb_nl_family_members")
@Data
@NoArgsConstructor
public class FamilyMember {
    @EmbeddedId
    private FamilyMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("familyId")
    @JoinColumn(name = "familyId")
    private Family family;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_role", nullable = false, length = 20)
    private String role;

    @CreationTimestamp
    @Column(name = "joined_at")
    private Instant joinedAt;

}
