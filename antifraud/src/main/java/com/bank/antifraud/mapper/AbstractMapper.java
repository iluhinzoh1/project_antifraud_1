package com.bank.antifraud.mapper;

import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.MappingTarget;

public interface AbstractMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
    void updateFromDto(D dto, @MappingTarget E entity);
    D eventToDto(TransferChecked event);
}
