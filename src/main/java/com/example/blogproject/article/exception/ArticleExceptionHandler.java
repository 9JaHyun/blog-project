package com.example.blogproject.article.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.blogproject.article")
public class ArticleExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> errorToCreateArticle(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(e.getMessage());
    }
}
