package com.example.blogproject.comment.controller;

import com.example.blogproject.comment.dto.CommentDeleteRequestDto;
import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.comment.dto.CommentSaveRequestDto;
import com.example.blogproject.comment.service.CommentService;
import com.example.blogproject.comment.dto.CommentUpdateRequestDto;
import com.example.blogproject.config.security.MemberDetailsImpl;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 목록 보기
    @GetMapping("/api/comment/{articlesId}")
    public ResponseEntity<Result<List<CommentResponseDto>>> showComments(@PathVariable Long articlesId) {
        return ResponseEntity.status(HttpStatus.OK)
              .body(new Result<List<CommentResponseDto>>(commentService.showComments(articlesId)));
    }

    // 댓글 작성
    @PostMapping("/api/comment")
    public ResponseEntity<String> writeComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentSaveRequestDto dto) {
        System.out.println("테스트");
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.writeComment(dto);
        return ResponseEntity.status(HttpStatus.OK)
              .body("성공!");
    }

    // 댓글 수정
    @PutMapping("/api/comment")
    public ResponseEntity<String> updateComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentUpdateRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.updateComment(dto);
        return ResponseEntity.status(HttpStatus.OK)
              .body("성공!");
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment")
    public ResponseEntity<String> deleteComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentDeleteRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.deleteComment(dto);
        return ResponseEntity.status(HttpStatus.OK)
              .body("성공!");
    }

    private static class Result<T>{
        private T data;
        public Result(T data) {
            this.data = data;
        }
    }
}
