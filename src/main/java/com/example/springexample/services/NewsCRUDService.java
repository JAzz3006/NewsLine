package com.example.springexample.services;
import com.example.springexample.dto.NewsItem;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class NewsCRUDService implements CRUDService<NewsItem>{
    private final AtomicLong idCounter = new AtomicLong(0);
    private final ConcurrentHashMap<Long, NewsItem> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<NewsItem> getById(Long id) {
        if (id == null){
            System.out.println("Something is wrong with the id that you use: " + id);
            return Optional.empty();
        }
        NewsItem value = storage.get(id);
        if (value == null){
            System.out.println("No object with such id: " + id);
            return Optional.empty();
        }
        return Optional.of(value);
    }

    @Override
    public Collection<NewsItem> getAll() {
        return storage.values();
    }

    @Override
    public void create(NewsItem item) {
        Long key = idCounter.incrementAndGet();
        item.setId(key);
        storage.put(key, item);
    }

    @Override
    public void edit(Long id, NewsItem item) {
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
    public void del(Long id) {
        if (!storage.containsKey(id)){
            System.out.println("No element to delete with id: " + id);
            return;
        }
        storage.remove(id);
    }
}

