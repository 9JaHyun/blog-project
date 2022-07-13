package com.example.blogproject.member.service;

import com.example.blogproject.member.domain.Member;
import com.example.blogproject.member.dto.JoinRequestDto;
import com.example.blogproject.member.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
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

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // Entity Insert
        memberRepository.save(member);
        return "성공";
    }
}
