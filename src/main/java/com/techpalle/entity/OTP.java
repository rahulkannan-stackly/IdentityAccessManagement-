package com.techpalle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp",indexes = { @Index(name = "idx_otp_user_id",columnList = "user_id"),
        @Index(name = "idx_otp_code",columnList = "code")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "code",nullable = false,length = 10)
    private String code;

    @Column(name = "expiry_date",nullable = false)
    private LocalDateTime expiryDate;

    @Builder.Default
    @Column(name = "is_used",nullable = false)
    private Boolean isUsed = false;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id",nullable = false,foreignKey = @ForeignKey(name = "fk_otp_user_id"))
    private User user;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return !Boolean.TRUE.equals(isUsed) && !isExpired();
    }
}
