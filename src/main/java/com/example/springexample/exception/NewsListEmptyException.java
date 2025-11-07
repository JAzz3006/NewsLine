package com.example.springexample.exception;

public class NewsListEmptyException extends RuntimeException{
    public NewsListEmptyException(){
        super("No news for now");
    }
}
