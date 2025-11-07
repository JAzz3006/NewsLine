package com.example.springexample.exception;

public class CategoryListEmptyException extends RuntimeException{
    public CategoryListEmptyException(){
        super("Category list is empty");
    }
}
