package com.example.nestedintervalwithfarey.service.core;

public interface CrudService<T, ID> {
    T find(ID id);
    T save(T node);
    void delete(ID id);
}
