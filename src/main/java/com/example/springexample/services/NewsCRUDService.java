package com.example.springexample.services;
import com.example.springexample.dto.News;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NewsCRUDService implements CRUDService<News>{
    private final AtomicLong idCounter = new AtomicLong(0);
    private final ConcurrentHashMap<Long, News> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<News> getById(Long id) {
        if (id == null){
            System.out.println("Something is wrong with the id that you use: " + id);
            return Optional.empty();
        }
        News value = storage.get(id);
        if (value == null){
            System.out.println("No object with such id: " + id);
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public Collection<News> getAll() {
        return storage.values();
    }

    @Override
    public void create(News item) {
        Long key = idCounter.incrementAndGet();
        item.setId(key);
        storage.put(key, item);
    }

    @Override
    public void update(Long id, News item) {
        if (!storage.containsKey(id)){
            System.out.println("No element with id: " + id);
            return;
        }
        if (item == null){
            System.out.println("This piece of news is null");
            return;
        }
        if (item.getText().isEmpty()){
            System.out.println("This piece of news is empty");
            return;
        }
        item.setId(id);
        storage.put(id, item);
    }

    @Override
    public boolean deleteById(Long id) {
        if (!storage.containsKey(id)){
            System.out.println("No element to delete with id: " + id);
            return false;
        }
        storage.remove(id);
        return true;
    }
}

