package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousPhoneTransferDto;
import com.bank.antifraud.Entities.SuspiciousPhoneTransfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneTransferMapper extends AbstractMapper<SuspiciousPhoneTransferDto, SuspiciousPhoneTransfer> {
}
