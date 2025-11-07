package com.example.springexample.services;
import com.example.springexample.dto.NewsDto;
import com.example.springexample.entity.Category;
import com.example.springexample.entity.News;
import com.example.springexample.exception.CategoryNotFoundException;
import com.example.springexample.exception.NewsListEmptyException;
import com.example.springexample.exception.NewsNotFoundException;
import com.example.springexample.repositories.CategoryRepository;
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
    private final NewsRepository newsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public NewsDto getById(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        return mapToDto(news);
    }

    @Override
    public List<NewsDto> getAll() {
        List<NewsDto> newsDtoList = newsRepository.findAll().stream()
                .map(NewsCRUDService::mapToDto)
                .toList();
        if (newsDtoList.isEmpty()){
            throw new NewsListEmptyException();
        }
        return newsDtoList;
    }

    @Override
    public NewsDto create(NewsDto newsDto) {
        log.info("Create news");
        News news = mapToEntity(newsDto);
        Long categoryId = newsDto.getCategoryId();
        log.info("Category id = {}, title = {}? text = {}", categoryId, news.getTitle(), news.getText());
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));
        news.setCategory(category);
        log.info("Category {}, text = {}", news.getCategory(), news.getText());
        News saved = new News();
        try {
            saved = newsRepository.save(news);
        } catch (Exception ex) {
            log.info("ошибка при сохранении новости: {}", ex.getMessage());
            throw ex;
        }

        return mapToDto(saved);
    }

    @Override
    public void update(NewsDto newsDto) {
        News news = mapToEntity(newsDto);
        Long id = newsDto.getId();
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
        news.setCategory(category);
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
        return news;
    }

    public static NewsDto mapToDto (News news){
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setText(news.getText());
        newsDto.setTime(news.getTime());
        log.info("category = {}", news.getCategory());
        newsDto.setCategoryId(news.getCategory().getId());
        return newsDto;
    }
}

