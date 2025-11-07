package com.example.springexample.controllers;
import com.example.springexample.dto.NewsDto;
import com.example.springexample.services.NewsCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsCRUDService newsService;

//    public NewsController (NewsCRUDService newsService){
//        this.newsService = newsService;
//    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id){
        NewsDto newsDto = newsService.getById(id);
        return ResponseEntity.ok(newsDto);
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews (){
        List<NewsDto> allNews = newsService.getAll();
        return ResponseEntity.ok(allNews);
    }

    @PostMapping
    public ResponseEntity<NewsDto> createOneNewsItem (@RequestBody NewsDto newsDto){
        NewsDto saved = newsService.create(newsDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
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
