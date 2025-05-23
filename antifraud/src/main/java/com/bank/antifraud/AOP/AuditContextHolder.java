package com.bank.antifraud.AOP;

import com.bank.antifraud.DTO.AuditDto;
import org.springframework.stereotype.Component;
/**
 * Класс для соблюдения единственной ответственности, для хранения промужеточных данных
 */

@Component
public class AuditContextHolder {
    private final ThreadLocal<AuditDto> context = new ThreadLocal<>();
    public void setOldAudit(AuditDto dto) {
        context.set(dto);
    }
    public AuditDto getOldAudit() {
        return context.get();
    }
    public void clear() {
        context.remove();
    }
}
