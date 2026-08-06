package com.techpalle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_tokens", indexes = { @Index(name = "idx_refresh_token",columnList = "token",unique = true),
        @Index(name = "idx_refresh_token_user_id",columnList = "user_id")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column( name = "token",nullable = false,unique = true,columnDefinition = "TEXT" )
    private String token;

    @Column(name = "expiry_date",nullable = false)
    private LocalDateTime expiryDate;

    @Builder.Default
    @Column( name = "is_revoked", nullable = false)
    private Boolean isRevoked = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_refresh_tokens_user_id"))
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return !Boolean.TRUE.equals(isRevoked) && !isExpired();
    }
}

