package com.bank.antifraud.AOP;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.Enum.OperationType;
import com.bank.antifraud.Services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Класс аспект, для перехватки и выставления значений в поля подозрительных запросов
 */


@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private static final String CREATE_PREFIX = "create";
    private static final String GET_ID_METHOD_NAME = "getId";
    private static final String SYSTEM = "system";
    private final AuditService auditService;
    private final ObjectMapper objectMapper;
    private final Clock clock;
    private final AuditContextHolder auditContextHolder;


    @Before("execution(* com.bank.antifraud.Services.*.update*(Long,..)) && args(id,..)")
    public void captureOldAudit(JoinPoint jp, Long id) {
        final String entityType = AuditUtils.getEntityTypeFromService(jp.getTarget().getClass());
        final AuditDto prev = auditService.findLastAudit(entityType, id);
        prev.setId(id);
        auditContextHolder.setOldAudit(prev);
    }

    @AfterReturning(
            pointcut = "execution(* com.bank.antifraud.Services.*.create*(..)) || " +
                    "execution(* com.bank.antifraud.Services.*.update*(..))",
            returning = "result"
    )
    public void auditCreateOrUpdate(JoinPoint jp, Object result) {
        final boolean isCreate = jp.getSignature().getName().startsWith(CREATE_PREFIX);
        final String user = getCurrentUser();
        final LocalDateTime now = LocalDateTime.now(clock);

        final AuditDto dto = new AuditDto();
        dto.setEntityType(AuditUtils.getEntityType(result.getClass()));
        dto.setOperationType(String.valueOf(isCreate ? OperationType.CREATE : OperationType.UPDATE));
        dto.setModifiedBy(user);
        dto.setNewEntityJson(serialize(result));

        if (isCreate) {
            final Long id = extractId(result);
            dto.setId(id);
            dto.setEntityJson(dto.getNewEntityJson());
            dto.setCreatedBy(user);
            dto.setCreatedAt(now);
            dto.setModifiedAt(now);
        } else {
            final AuditDto prev = auditContextHolder.getOldAudit();
            auditContextHolder.clear();
            dto.setId(prev.getId());
            dto.setEntityJson(prev.getNewEntityJson());
            dto.setCreatedBy(prev.getCreatedBy());
            dto.setCreatedAt(prev.getCreatedAt());
            dto.setModifiedAt(now);
        }

        auditService.logAudit(dto);
    }

    private String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize for audit", e);
            return "{}";
        }
    }

    /** Через рефлексию вызывает getId() на DTO */
    private Long extractId(Object dto) {
        try {
            final Method m = dto.getClass().getMethod(GET_ID_METHOD_NAME);
            final Object id = m.invoke(dto);
            return (id instanceof Long) ? (Long) id : null;
        } catch (Exception e) {
            log.error("Cannot extract id from DTO", e);
            return null;
        }
    }

    private String getCurrentUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (authentication != null) ? authentication.getName() : SYSTEM;
    }
}


