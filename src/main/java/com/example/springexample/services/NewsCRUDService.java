package com.example.springexample.services;
import com.example.springexample.dto.NewsDto;
import com.example.springexample.entity.News;
import com.example.springexample.exception.NewsNotFoundException;
import com.example.springexample.repositories.NewsRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@Getter
@RequiredArgsConstructor
public class NewsCRUDService implements CRUDService<NewsDto>{
//    private final AtomicLong idCounter = new AtomicLong(0);
//    private final ConcurrentHashMap<Long, NewsDto> storage = new ConcurrentHashMap<>();
    private final NewsRepository newsRepository;

    @Override
    public NewsDto getById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        return mapToDto(news);
    }

    @Override
    public List<NewsDto> getAll() {
        return newsRepository.findAll().stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
    }

    @Override
    public void create(NewsDto newsDto) {
        newsRepository.save(mapToEntity(newsDto));
    }

    @Override
    public void update(NewsDto newsDto) {
        Long id = newsDto.getId();
        newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        News news = mapToEntity(newsDto);
        newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        newsRepository.deleteById(id);
    }

    public static News mapToEntity(NewsDto newsDto){
        News news = new News();
        news.setId(newsDto.getId());
        news.setTitle(newsDto.getTitle());
        news.setText(newsDto.getText());
        //категорию добавить
        return news;
    }

    public static NewsDto mapToDto (News news){
        return new NewsDto();
    }
}

