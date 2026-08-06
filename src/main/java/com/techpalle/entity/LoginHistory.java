package com.techpalle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "login_history",indexes = {@Index(name = "idx_login_history_user_id", columnList = "user_id"),
        @Index(name = "idx_login_history_timestamp",columnList = "login_timestamp")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column( name = "login_timestamp",nullable = false)
    private LocalDateTime loginTimestamp;

    @Column(name = "logout_timestamp")
    private LocalDateTime logoutTimestamp;

    @Column( name = "ip_address", length = 50)
    private String ipAddress;

    @Column( name = "user_agent",columnDefinition = "TEXT")
    private String userAgent;

    @Column( name = "login_status",nullable = false,length = 20 )
    private String loginStatus;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn( name = "user_id", nullable = false,foreignKey = @ForeignKey(name = "fk_login_history_user_id"))
    private User user;

    @PrePersist
    protected void onCreate() {
        if (loginTimestamp == null) {
            loginTimestamp = LocalDateTime.now();
        }
    }
}

