package com.bank.antifraud.Repositories;

import com.bank.antifraud.Entities.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для аудита.
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

}
