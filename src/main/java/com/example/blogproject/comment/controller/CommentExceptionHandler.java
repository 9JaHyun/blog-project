package com.example.blogproject.comment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.blogproject.comment")
public class CommentExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> errorToCreateArticle(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(e.getMessage());
    }
}
