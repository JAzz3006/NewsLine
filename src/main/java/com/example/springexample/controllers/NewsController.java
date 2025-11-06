package com.example.springexample.controllers;
import com.example.springexample.dto.NewsDto;
import com.example.springexample.services.NewsCRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsCRUDService newsService;

    public NewsController (NewsCRUDService newsService){
        this.newsService = newsService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id){
        NewsDto newsDto = newsService.getById(id);
        return ResponseEntity.ok(newsDto);
    }

    @GetMapping
    public ResponseEntity<?> getAllNews (){
        Collection<NewsDto> allNews = newsService.getAll();
        if (allNews.isEmpty()){
            Map<String, String> responseIfEmpty = Map.of("message", "no news for now");
            return ResponseEntity.status(HttpStatus.OK).body(responseIfEmpty);
        }
        return ResponseEntity.ok(allNews);
    }

    @PostMapping
    public ResponseEntity<NewsDto> createOneNewsItem (@RequestBody NewsDto item){
        newsService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping
    public ResponseEntity<NewsDto> updateNewsItem(@RequestBody NewsDto item){
        newsService.update(item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<NewsDto> deleteMewsItem (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
