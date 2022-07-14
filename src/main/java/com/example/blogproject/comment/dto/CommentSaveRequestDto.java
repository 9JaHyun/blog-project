package com.example.blogproject.comment.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentSaveRequestDto {

    @NotNull
    private String content;
    private String username;
    @NotNull
    private Long articlesId;
}
