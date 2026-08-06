package com.techpalle.dto.role;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequest {

    @NotNull(message = "User id is required")
    private Long userId;

    @NotEmpty(message = "At least one role id must be provided")
    private Set<Long> roleIds;
}
