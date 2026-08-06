package com.techpalle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techpalle.entity.AuditLog;
import com.techpalle.entity.User;
import java.io.Serializable;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Serializable> {

    List<AuditLog> findByUser(User user);

    List<AuditLog> findByActionType(String actionType);
}
