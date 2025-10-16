package com.example.springexample.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class News {
    public News(Long id, String title, String text){
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = Instant.now().truncatedTo(ChronoUnit.SECONDS);
    }
    private Long id;
    private String title;
    private String text;
    private Instant date;
}
