package com.bank.antifraud.mapper;

import com.bank.antifraud.DTO.SuspiciousCardTransferDto;
import com.bank.antifraud.Entities.SuspiciousCardTransfer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardTransferMapper extends AbstractMapper<SuspiciousCardTransferDto, SuspiciousCardTransfer>  {

}
