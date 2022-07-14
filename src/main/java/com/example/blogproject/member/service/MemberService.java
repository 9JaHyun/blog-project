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
            throw new IllegalArgumentException("비밀번호와 비밀번호 재확인은 일치해야 합니다.");
        }

        if (password.contains(nickname)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어서는 안됩니다.");
        }

        if (memberRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("중복되는 닉네임 입니다.");
        }

        // dto => entity 로 변환
        Member member = dto.toEntity();

        member.setPassword(passwordEncoder.encode(member.getPassword()));

        // Entity Insert
        memberRepository.save(member);
        return "성공";
    }
}
