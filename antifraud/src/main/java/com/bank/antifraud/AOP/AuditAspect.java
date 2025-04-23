package com.bank.antifraud.AOP;

import com.bank.antifraud.DTO.AuditDto;
import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Services.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
    private final AuditService auditService;
    private final ObjectMapper objectMapper;

    @Pointcut("execution(* com.bank.antifraud.Services.Suspicious*TransferServiceImpl.update*(..)) || " +
            "execution(* com.bank.antifraud.Services.Suspicious*TransferServiceImpl.create*(..))")
    public void auditPointcut() {
    }

    @AfterReturning(value = "auditPointcut()", returning = "result")
    public void beforeCreateAndUpdate(JoinPoint point, Object result) {
        try {
            final String methodName = point.getSignature().getName();
            final String operationType = methodName.startsWith("create") ? "CREATE" : "UPDATE";
            final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            final String createdBy = authentication.getName();
            final String entityType = resolveResult(result);
            final AuditDto auditDto = new AuditDto();
            auditDto.setModifiedAt(LocalDateTime.now());
            auditDto.setModifiedBy(createdBy);
            auditDto.setNewEntityJson(objectMapper.writeValueAsString(result));
            auditDto.setCreatedAt(LocalDateTime.now());
            auditDto.setEntityType(entityType);
            auditDto.setOperationType(operationType);
            auditDto.setCreatedBy(createdBy);
            auditDto.setEntityJson(objectMapper.writeValueAsString(result));
            auditService.logAudit(auditDto);
            LOGGER.info("audit success logged:{}", auditDto);
        } catch (JsonProcessingException e) {
            LOGGER.error("audit has Exception {}: {}", point.getSignature().getName(), e.getMessage());
        }
    }

    public String resolveResult(Object entity) {
        if (entity instanceof SuspiciousCardTransferDto) {
            return "SuspiciousCardTransfer";
        } else if (entity instanceof SuspiciousPhoneTransferDto) {
            return "SuspiciousPhoneTransfer";
        } else if (entity instanceof SuspiciousAccountTransferDto) {
            return "SuspiciousAccountTransfer";
        }
        return "unknown";
    }
}
