package com.example.blogproject.member.controller;

import com.example.blogproject.member.dto.JoinRequestDto;
import com.example.blogproject.member.dto.LoginRequestDto;
import com.example.blogproject.member.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController     // JSON 파일 반환
//@Controller         // HTML 파일 반환
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // [GET, POST] => 서버 리소스에 변화를 주느냐 안주느냐
    // 회원가입
    @PostMapping("/join")
    public String join(@RequestBody JoinRequestDto dto) {
        return memberService.join(dto);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto dto) {
        return dto.toString();
    }
}
