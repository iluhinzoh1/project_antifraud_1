package com.bank.antifraud.AOP;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Repositories.AuditRepository;
import com.bank.antifraud.Services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final AuditRepository repository;

    @AfterReturning(
            pointcut = "execution(* com.bank.antifraud.Services.*.create*(..)) || " +
                    "execution(* com.bank.antifraud.Services.*.update*(..))",
            returning = "result"
    )
    public void logAuditEvent(JoinPoint jp, Object result) throws JsonProcessingException {
        final String operation = jp.getSignature().getName().startsWith("create") ? "CREATE" : "UPDATE";
        final String currentUser = getCurrentUser();
        final String entityType = result.getClass().getSimpleName().replace("Dto", "");
        final AuditDto auditDto = new AuditDto();
        auditDto.setOperationType(operation);
        auditDto.setEntityType(entityType);
        auditDto.setCreatedBy(currentUser);
        auditDto.setModifiedBy(currentUser);
        auditDto.setCreatedAt(LocalDateTime.now()); // надо поменять
        auditDto.setModifiedAt(LocalDateTime.now());
        auditDto.setNewEntityJson(objectMapper.writeValueAsString(result));
        auditDto.setEntityJson(objectMapper.writeValueAsString(result));
        if (operation.startsWith("UP")) {
            final Long id = (Long) jp.getArgs()[0];
            final Object oldEntity = getOldEntity(id);
            auditDto.setEntityJson(objectMapper.writeValueAsString(oldEntity));
        }
        auditService.logAudit(auditDto);
    }

    private Object getOldEntity(Long id) {
        return repository.findById(id).orElse(null);
    }

    public String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
