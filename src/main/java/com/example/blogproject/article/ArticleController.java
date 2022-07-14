package com.example.blogproject.article;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public List<ArticlesDto> articles() {
        return articleService.articles();
    }

    @GetMapping("/api/articles/{articleId}")
    public String articles(Model model, @PathVariable Long articleId) {
        ArticlesDto dto = articleService.findOne(articleId);
        model.addAttribute("article", dto);
        return "article";
    }

    @PostMapping("/api/articles")
    public String writeArticle(ArticleSaveDto dto) {
        log.info(dto.toString());
        articleService.writeArticle(dto);
        return "redirect:/";
    }
}
