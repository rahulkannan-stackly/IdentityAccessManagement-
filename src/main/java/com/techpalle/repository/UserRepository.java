package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.User;
import java.io.Serializable;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Serializable> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}