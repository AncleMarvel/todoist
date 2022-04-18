package com.example.todo.util;

import org.modelmapper.TypeMap;

public abstract class Mapper <E, D> {

    public E toEntity(D dto) {
        return getToEntityMapper().map(dto);
    }

    public D toDto(E entity) {
        return getToDtoMapper().map(entity);
    }

    protected abstract TypeMap<E, D> getToDtoMapper();

    protected abstract TypeMap<D, E> getToEntityMapper();

}
