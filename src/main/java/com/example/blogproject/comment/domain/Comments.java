package com.example.blogproject.comment.domain;

import com.example.blogproject.article.domain.Articles;
import com.example.blogproject.member.domain.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    // Writer -> 회원이랑 직접 연관
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 댓글 -> 게시글의 댓글
    @ManyToOne
    @JoinColumn(name = "articles_id")
    private Articles articles;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime updatedAt;

    @LastModifiedBy
    @Column(insertable = false)
    private String updatedBy;

    public Comments() {
    }

    private Comments(String content, Member member, Articles articles) {
        this.content = content;
        this.member = member;
        this.articles = articles;
    }

    public static Comments createComments(String content, Member member, Articles articles) {
        return new Comments(content, member, articles);
    }
}
