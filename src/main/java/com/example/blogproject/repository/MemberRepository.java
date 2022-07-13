package com.example.blogproject.repository;


import com.example.blogproject.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // "Select member from Member where nickname = ?"
    Member findByNickname(String nickname);
}
