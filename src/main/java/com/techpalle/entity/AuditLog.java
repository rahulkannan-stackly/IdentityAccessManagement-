package com.techpalle.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "audit_logs",indexes = { @Index(name = "idx_audit_logs_user_id", columnList = "user_id" ),
        @Index( name = "idx_audit_logs_action_type", columnList = "action_type" ),
        @Index( name = "idx_audit_logs_timestamp",columnList = "audit_timestamp" )})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "action_type", nullable = false,length = 100)
    private String actionType;

    @Column( name = "entity_type",nullable = false,length = 100)
    private String entityType;

    @Column(name = "entity_id")
    private Long entityId;

    @Column( name = "old_value",columnDefinition = "TEXT")
    private String oldValue;

    @Column(name = "new_value",columnDefinition = "TEXT")
    private String newValue;

    @Column( name = "ip_address",length = 50)
    private String ipAddress;

    @Column( name = "user_agent", columnDefinition = "TEXT")
    private String userAgent;

    @Column( name = "audit_timestamp",nullable = false)
    private LocalDateTime auditTimestamp;

    @Column(name = "status", nullable = false,length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn( name = "user_id", foreignKey = @ForeignKey(name = "fk_audit_logs_user_id"))
    private User user;

    @PrePersist
    protected void onCreate() {
        if (auditTimestamp == null) {
            auditTimestamp = LocalDateTime.now();
        }
    }
}

