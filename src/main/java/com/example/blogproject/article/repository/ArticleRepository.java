package com.example.blogproject.article.repository;

import com.example.blogproject.article.domain.Articles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Articles, Long> {

}
