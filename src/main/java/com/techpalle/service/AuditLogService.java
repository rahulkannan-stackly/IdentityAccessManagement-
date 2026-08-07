package com.techpalle.service;

import com.techpalle.entity.User;

public interface AuditLogService {

    void saveAuditLog( String actionType,String entityType,Long entityId,String status, User user);
}