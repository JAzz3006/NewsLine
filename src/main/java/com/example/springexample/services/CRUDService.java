package com.example.springexample.services;
import java.util.Collection;
import java.util.Optional;

public interface CRUDService<T> {
    Optional<T> getById(Long id);
    Collection<T> getAll();
    void create (T item);
    void update(Long id, T item);
    boolean deleteById(Long id);
}
