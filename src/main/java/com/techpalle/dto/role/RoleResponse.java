package com.techpalle.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponse {

    private Long id;

    private String name;

    private String description;

    private Boolean isActive;

    private Set<String> permissions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}