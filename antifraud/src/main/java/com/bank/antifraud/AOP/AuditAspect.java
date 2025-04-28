package com.bank.antifraud.AOP;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final Clock clock;

    private final ThreadLocal<AuditDto> oldAudit = new ThreadLocal<>();

    @Before("execution(* com.bank.antifraud.Services.*.update*(Long,..)) && args(id,..)")
    public void captureOldAudit(JoinPoint jp, Long id) {
        final String entityType = jp.getTarget().getClass()
                .getSimpleName()
                .replace("ServiceImpl", "")
                .replace("Service", "");
        oldAudit.set(auditService.findLastAudit(entityType, id));
    }

    @AfterReturning(
            pointcut = "execution(* com.bank.antifraud.Services.*.create*(..)) || " +
                    "          execution(* com.bank.antifraud.Services.*.update*(..))",
            returning = "result"
    )
    public void auditCreate(JoinPoint jp, Object result) {
        final String user = SecurityContextHolder.getContext().getAuthentication().getName();
        final String entityType = result.getClass().getSimpleName().replace("Dto", "");
        final boolean isCreate = jp.getSignature().getName().startsWith("create");
        final LocalDateTime now = LocalDateTime.now(clock);

        final AuditDto dto = new AuditDto();
        dto.setEntityType(entityType);
        dto.setOperationType(isCreate ? "CREATE" : "UPDATE");
        dto.setNewEntityJson(serialize(result));

        if (isCreate) {
            dto.setEntityJson(dto.getNewEntityJson());
            dto.setCreatedAt(now);
            dto.setCreatedBy(user);
            dto.setModifiedBy(user);
            dto.setModifiedAt(now);
        } else {
            final AuditDto prev = oldAudit.get();
            oldAudit.remove();
            dto.setEntityJson(prev.getNewEntityJson());
            dto.setCreatedAt(prev.getCreatedAt());
            dto.setCreatedBy(prev.getCreatedBy());
            dto.setModifiedBy(user);
            dto.setModifiedAt(now);
        }

        auditService.logAudit(dto);
        log.info("Audit {} logged for {}: old={} new={}",
                dto.getOperationType(),
                entityType,
                dto.getEntityJson(),
                dto.getNewEntityJson());
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON error", e);
            return "{}";
        }
    }
}

