package com.example.blogproject.article.controller;

import com.example.blogproject.article.dto.ArticleSaveDto;
import com.example.blogproject.article.dto.ArticlesDto;
import com.example.blogproject.article.service.ArticleService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @ResponseBody
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticlesDto>> articles() {
        return ResponseEntity.status(HttpStatus.OK)
              .body(articleService.articles());
    }

    @GetMapping("/api/articles/{articleId}")
    public String articles(Model model, @PathVariable Long articleId) {
        ArticlesDto dto = articleService.findOne(articleId);
        model.addAttribute("article", dto);
        return "article";
    }

    @PostMapping("/api/articles")
    public String writeArticle(@Validated ArticleSaveDto dto) {
        log.info(dto.toString());
        articleService.writeArticle(dto);
        return "redirect:/";
    }
}
