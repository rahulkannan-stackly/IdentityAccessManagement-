package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.Permission;
import java.io.Serializable;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Serializable> {

    Optional<Permission> findByCode(String code);

    boolean existsByCode(String code);
}