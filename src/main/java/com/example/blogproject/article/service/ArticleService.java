package com.example.blogproject.article.service;

import com.example.blogproject.article.repository.ArticleRepository;
import com.example.blogproject.article.dto.ArticleSaveDto;
import com.example.blogproject.article.domain.Articles;
import com.example.blogproject.article.dto.ArticlesDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public void writeArticle(ArticleSaveDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public List<ArticlesDto> articles() {
        return articleRepository.findAll()
              .stream()
              .map(ArticlesDto::new)
              .collect(Collectors.toList());
    }

    public ArticlesDto findOne(Long articleId) {
        Articles entity = articleRepository.findById(articleId)
              .orElseThrow(() -> new IllegalArgumentException("없는 게시글입니다"));

        return new ArticlesDto(entity);
    }
}
