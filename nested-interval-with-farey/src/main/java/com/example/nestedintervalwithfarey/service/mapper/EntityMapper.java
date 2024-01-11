package com.example.nestedintervalwithfarey.service.mapper;

public interface EntityMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
    E clone(E entity);
}
