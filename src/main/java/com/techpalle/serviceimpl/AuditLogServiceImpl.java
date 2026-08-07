package com.techpalle.serviceimpl;

import com.techpalle.entity.AuditLog;
import com.techpalle.entity.User;
import com.techpalle.repository.AuditLogRepository;
import com.techpalle.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    public void saveAuditLog( String actionType,String entityType, Long entityId,String status,User user) 
    {
        log.info("Creating audit log - Action: {}, Entity: {}, EntityId: {}, Status: {}",
                actionType,entityType, entityId,status);

        AuditLog auditLog = AuditLog.builder().actionType(actionType).entityType(entityType).entityId(entityId)
        		.status(status).user(user).build();

        auditLogRepository.save(auditLog);

        log.info( "Audit log saved successfully - Action: {}, EntityId: {}",actionType,entityId);
    }
}
