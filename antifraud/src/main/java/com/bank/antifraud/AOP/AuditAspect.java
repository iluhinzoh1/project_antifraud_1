package com.bank.antifraud.AOP;

import com.bank.antifraud.Services.AuditService;
import com.bank.antifraud.mapper.AuditMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuditAspect.class);
    private AuditService auditService;
    private AuditMapper mapper;

    @Pointcut("execution(* com.bank.antifraud.Services.Suspicious*TransferServiceImpl.update*(..)) || " +
            "execution(* com.bank.antifraud.Services.Suspicious*TransferServiceImpl.create*(..))")
    public void auditPointcut() {
    }

    @AfterReturning(value = "auditPointcut()", returning = "result")
    public void beforeCreateAndUpdate(JoinPoint point, Object result) {

        LOGGER.info("");
    }
}
