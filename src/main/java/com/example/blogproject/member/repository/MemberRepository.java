package com.example.blogproject.member.repository;


import com.example.blogproject.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // "Select member from Member where nickname = ?"
    Member findByNickname(String nickname);

    Optional<Member> findByUsername(String username);
}
