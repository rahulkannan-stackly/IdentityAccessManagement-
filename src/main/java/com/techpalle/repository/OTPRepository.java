package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.OTP;
import com.techpalle.entity.User;
import java.io.Serializable;
import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Serializable> {

    Optional<OTP> findByCodeAndUser(String code, User user);

    void deleteByUser(User user);
}