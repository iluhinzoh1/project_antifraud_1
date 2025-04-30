package com.bank.antifraud.mapper;

import org.mapstruct.MappingTarget;

public interface AbstractMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
    void updateFromDto(D dto, @MappingTarget E entity);
}
