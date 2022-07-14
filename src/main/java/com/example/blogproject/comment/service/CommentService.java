package com.example.blogproject.comment.service;

import com.example.blogproject.article.repository.ArticleRepository;
import com.example.blogproject.article.domain.Articles;
import com.example.blogproject.comment.domain.Comments;
import com.example.blogproject.comment.dto.CommentDeleteRequestDto;
import com.example.blogproject.comment.dto.CommentResponseDto;
import com.example.blogproject.comment.dto.CommentSaveRequestDto;
import com.example.blogproject.comment.dto.CommentUpdateRequestDto;
import com.example.blogproject.comment.repository.CommentRepository;
import com.example.blogproject.member.domain.Member;
import com.example.blogproject.member.repository.MemberRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;

    public CommentService(CommentRepository commentRepository, MemberRepository memberRepository,
          ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.articleRepository = articleRepository;
    }

    // 댓글 목록 보기
    public List<CommentResponseDto> showComments(Long articlesId) {
//         모든 게시글의 모든 댓글을 불러오기
//        commentRepository.findAll();

        // 특정 게시글의 모든 댓글 불러오기
        return commentRepository.findByArticlesIdOrderByCreatedAtDesc(articlesId)
              .stream()
              .map(CommentResponseDto::new)
              .collect(Collectors.toList());
    }

    // 댓글 작성
    public void writeComment(CommentSaveRequestDto dto) {
        Member member = memberRepository.findByUsername(dto.getUsername())
              .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));

        Articles articles = articleRepository.findById(dto.getArticlesId())
              .orElseThrow(() -> new IllegalArgumentException("잘못된 게시글입니다."));

        // Comment 저장
        commentRepository.save(Comments.createComments(dto.getContent(), member, articles));
    }

    // 댓글 수정
    public void updateComment(CommentUpdateRequestDto dto) {
        Comments comments = commentRepository.findById(dto.getId())
              .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));

        // 댓글을 쓴 사람인가 검증
        if (!dto.getUsername().equals(comments.getMember().getUsername())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }

        comments.setContent(dto.getContent()); // 더티체킹
    }

    // 댓글 삭제
    public void deleteComment(CommentDeleteRequestDto dto) {
        Comments comments = commentRepository.findById(dto.getId())
              .orElseThrow(() -> new IllegalArgumentException("없는 댓글입니다."));

        // 댓글을 쓴 사람인가 검증
        if (!dto.getUsername().equals(comments.getMember().getUsername())) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
        commentRepository.deleteById(dto.getId());
    }
}
