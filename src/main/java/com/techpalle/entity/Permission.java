package com.techpalle.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "permissions",
    indexes = { @Index( name = "idx_permissions_code",columnList = "code", unique = true) })
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    
    @Column( name = "code", nullable = false, unique = true,length = 100)
    private String code;

    @Column(name = "name",nullable = false,length = 100)
    private String name;

    @Column( name = "description", length = 500)
    private String description;

    @Builder.Default
    @Column( name = "is_active",nullable = false)
    private Boolean isActive = true;

    @Column( name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column( name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder.Default
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();

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

