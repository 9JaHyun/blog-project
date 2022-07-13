package com.example.blogproject.service;

import com.example.blogproject.dto.JoinRequestDto;
import com.example.blogproject.repository.MemberRepository;
import com.example.blogproject.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(JoinRequestDto dto) {
        String password = dto.getPassword();
        String rePassword = dto.getRePassword();
        String nickname = dto.getNickname();

        if (!password.equals(rePassword)) {
            return "회원가입 실패";
        }

        if (password.contains(nickname)) {
            return "회원가입 실패";
        }

        if (memberRepository.findByNickname(nickname) != null) {
            return "중복된 닉네임입니다";
        }

        // dto => entity 로 변환
        Member member = dto.toEntity();

        // Entity Insert
        memberRepository.save(member);
        return "성공";
    }
}
