package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.RefreshToken;
import com.techpalle.entity.User;
import java.io.Serializable;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Serializable> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}
