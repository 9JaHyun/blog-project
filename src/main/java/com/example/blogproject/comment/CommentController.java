package com.example.blogproject.comment;

import com.example.blogproject.config.security.MemberDetailsImpl;
import java.util.List;
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
    public List<CommentResponseDto> showComments(@PathVariable Long articlesId) {
        return commentService.showComments(articlesId);
    }

    // 댓글 작성
    @PostMapping("/api/comment")
    public void writeComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentSaveRequestDto dto) {
        System.out.println("테스트");
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.writeComment(dto);
    }

    // 댓글 수정
    @PutMapping("/api/comment")
    public void updateComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentUpdateRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.updateComment(dto);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comment")
    public void deleteComment(@AuthenticationPrincipal MemberDetailsImpl memberDetails,
          @RequestBody CommentDeleteRequestDto dto) {
        dto.setUsername(memberDetails.getMember().getUsername());
        commentService.deleteComment(dto);
    }
}
