package com.example.blogproject.comment.repository;

import com.example.blogproject.comment.domain.Comments;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {

    List<Comments> findByArticlesIdOrderByCreatedAtDesc(Long articlesId);
}
