package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.LoginHistory;
import com.techpalle.entity.User;
import java.io.Serializable;
import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Serializable> {

    List<LoginHistory> findByUser(User user);
}
