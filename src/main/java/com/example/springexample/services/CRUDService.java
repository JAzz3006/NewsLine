package com.example.springexample.services;
import com.example.springexample.entity.News;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CRUDService<T> {
    T getById(Long id);
    List<T> getAll();
    T create (T item);
    void update(T item);
    void deleteById(Long id);
}
