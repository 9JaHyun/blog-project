package com.example.blogproject.dto;

import com.example.blogproject.domain.Member;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor      // 생성자를 하나라도 만들면 기본 생성자가 없어지기 때문에 명시적으로 생성
@AllArgsConstructor     // 테스트 코드용
@Getter //Spring이 자동으로 JSON => Class 변경을 위해 getter 가 필요
public class JoinRequestDto {
    private String username;
    @Size(min = 4)
    private String password;
    @Size(min = 4)
    private String rePassword;
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).{3,}$")
    private String nickname;

    public Member toEntity() {
        return new Member(username, password, nickname);
    }
}
