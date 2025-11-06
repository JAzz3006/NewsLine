package com.example.springexample.exception;

public class NewsNotFoundException extends RuntimeException{
    public NewsNotFoundException(Long id){
        super(String.format("No news with id: %d", id));
    }
}
