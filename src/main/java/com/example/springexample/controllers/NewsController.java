package com.example.springexample.controllers;
import com.example.springexample.dto.News;
import com.example.springexample.services.NewsCRUDService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsCRUDService newsService;

    public NewsController (NewsCRUDService newsService){
        this.newsService = newsService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getNewsItemById(@PathVariable Long id){
        return newsService.getById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet( () -> {
                    Map<String, String> errorBody = Map.of("message", String.format("No item with id %d", id));
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
                });
    }

    @GetMapping
    public ResponseEntity<?> getAllNews (){
        Collection<News> allNews = newsService.getAll();
        if (allNews.isEmpty()){
            Map<String, String> responseIfEmpty = Map.of("message", "no news for now");
            return ResponseEntity.status(HttpStatus.OK).body(responseIfEmpty);
        }
        return ResponseEntity.ok(allNews);
    }

    @PostMapping
    public ResponseEntity<News> createOneNewsItem (@RequestBody News item){
        newsService.create(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }

    @PutMapping
    public ResponseEntity<News> updateNewsItem(@RequestBody News item, @RequestParam Long id){
        newsService.update(id, item);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMewsItem (@PathVariable Long id){
        boolean deleted = newsService.deleteById(id);
        if (deleted){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else {
            Map<String, String> errorBody = Map.of("message",String.format("No news to delete with id %d", id));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
        }
    }

}
