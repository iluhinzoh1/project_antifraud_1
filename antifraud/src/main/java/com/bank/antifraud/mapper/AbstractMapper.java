package com.bank.antifraud.mapper;

import com.bank.antifraud.Entities.TransferChecked;
import org.mapstruct.MappingTarget;

/**
 * Абстрактный маппер для преобразования сущности в dto и обратно.
 */
public interface AbstractMapper<D, E> {
    D toDto(E entity);
    E toEntity(D dto);
    void updateFromDto(D dto, @MappingTarget E entity);
    D eventToDto(TransferChecked event);
}
