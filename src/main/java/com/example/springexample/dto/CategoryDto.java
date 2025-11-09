package com.example.springexample.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDto {
    private Long id;
    private String title;
    @JsonIgnore
    private List<NewsDto> newsDtoList = new ArrayList<>();
}
