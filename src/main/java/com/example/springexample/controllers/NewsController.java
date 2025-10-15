package com.example.springexample.controllers;
import com.example.springexample.dto.NewsItem;
import com.example.springexample.services.NewsCRUDService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsCRUDService newsService;

    public NewsController (NewsCRUDService newsService){
        this.newsService = newsService;
    }

    @GetMapping(path = "/{id}")
    public NewsItem getNewsItemById(@PathVariable Long id){
        if (newsService.getById(id).isPresent()){
            return newsService.getById(id).get();
        } else return new NewsItem(0L, "empty", "empty");
    }

    @GetMapping
    public Collection<NewsItem> getAllNews (){
        return newsService.getAll();
    }

    @PostMapping
    public void createOneNewsItem (@RequestBody NewsItem item){
        newsService.create(item);
    }

    @PutMapping(path = "/{id}")
    public void updateNewsItem(@RequestBody NewsItem item, @PathVariable Long id){
        newsService.edit(id, item);
    }

    @DeleteMapping
    public void deleteMewsItem (@RequestParam Long id){
        newsService.del(id);
    }

}
