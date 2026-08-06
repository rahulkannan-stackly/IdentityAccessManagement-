package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.Role;
import java.io.Serializable;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Serializable> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}
