package com.bank.antifraud.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для ....
 */
@Repository
public interface SuspiciousPhoneTransferRepository extends JpaRepository<SuspiciousPhoneTransferRepository, Long> {

}
