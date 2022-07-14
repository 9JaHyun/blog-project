package com.example.blogproject.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByArticlesIdOrderByCreatedAtDesc(Long articlesId);
}
