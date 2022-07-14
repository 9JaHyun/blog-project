package com.example.blogproject.article.dto;

import com.example.blogproject.article.domain.Articles;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleSaveDto {

    @NotNull
    private String title;

    @NotNull
    private String writer;

    @NotNull
    private String content;

    public Articles toEntity() {
        return new Articles(title, writer, content);
    }
}
