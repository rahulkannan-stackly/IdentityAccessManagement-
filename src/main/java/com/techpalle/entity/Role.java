package com.techpalle.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles",indexes = {@Index( name = "idx_roles_name",columnList = "name", unique = true ) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column( name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column( name = "description",length = 500)
    private String description;

    @Builder.Default
    @Column( name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column( name = "created_at", nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column( name = "updated_at", nullable = false )
    private LocalDateTime updatedAt;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "role_permissions",joinColumns = @JoinColumn(name = "role_id",
            foreignKey = @ForeignKey(name = "fk_role_permissions_role_id")),
        inverseJoinColumns = @JoinColumn( name = "permission_id",
        foreignKey = @ForeignKey(name = "fk_role_permissions_permission_id")))
    private Set<Permission> permissions = new HashSet<>();

    @Builder.Default
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
