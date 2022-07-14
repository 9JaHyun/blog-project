package com.example.blogproject.member.controller;

import com.example.blogproject.member.dto.JoinRequestDto;
import com.example.blogproject.member.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

//@RestController     // JSON 파일 반환
@Controller         // HTML 파일 반환
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

    // [GET, POST] => 서버 리소스에 변화를 주느냐 안주느냐
    // 회원가입
    @PostMapping("/user/signup")
    public String join(JoinRequestDto dto) {
        memberService.join(dto);
        return "redirect:/user/loginView";
    }
//
//    // 회원 관련 정보 받기
//    @PostMapping("/user/userinfo")
//    @ResponseBody
//    public Member getUserInfo(@AuthenticationPrincipal MemberDetailsImpl memberDetails) {
//        String username = memberDetails.getMember().getUsername();
//
//        return new Member(username, "1111", "dasdsa");
//    }

}
