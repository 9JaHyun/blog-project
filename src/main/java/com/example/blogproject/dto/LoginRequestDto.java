package com.example.blogproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor      // 생성자를 하나라도 만들면 기본 생성자가 없어지기 때문에 명시적으로 생성
@AllArgsConstructor     // 테스트 코드용
@Getter //Spring이 자동으로 JSON => Class 변경을 위해 getter 가 필요
public class LoginRequestDto {
    private String username;
    private String password;
}
