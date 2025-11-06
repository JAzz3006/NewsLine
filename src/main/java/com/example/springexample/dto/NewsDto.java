package com.example.springexample.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.Instant;

@Data
public class NewsDto {

    private Long id;
    private String title;
    private String text;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Instant time;
    private Long categoryId;

}
