package com.example.blogproject.comment;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentResponseDto {
    @NotNull
    private Long id;
    @NotNull
    private String content;
    @NotNull
    private String username;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Comments comments) {
        this.id = comments.getId();
        this.content = comments.getContent();
        this.username = comments.getMember().getUsername();
    }
}
