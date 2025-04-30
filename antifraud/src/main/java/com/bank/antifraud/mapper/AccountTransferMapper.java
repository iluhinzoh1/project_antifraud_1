package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousAccountTransferDto;
import com.bank.antifraud.Entities.SuspiciousAccountTransfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTransferMapper extends AbstractMapper<SuspiciousAccountTransferDto, SuspiciousAccountTransfer> {
}
