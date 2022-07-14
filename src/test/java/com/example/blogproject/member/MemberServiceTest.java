package com.example.blogproject.member;

import com.example.blogproject.member.dto.JoinRequestDto;
import com.example.blogproject.member.service.MemberService;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    // validation test
    private static Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @DisplayName("닉네임 3자리 미만인가?")
    @Test
    void nicknameLessThanThreeLetter() {
        // 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");

        // 닉네임 3자리 미만
        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
              new JoinRequestDto("user2", "1111", "1111", "us"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
              new JoinRequestDto("user3", "1111", "1111", "ua"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호가 4자 미만")
    @Test
    void passwordLessThanFourLetter() {
        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
              new JoinRequestDto("user4", "111", "111", "user1"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
              new JoinRequestDto("user5", "111", "111", "user2"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호에 닉네임이 포함되는 경우")
    @Test
    void containNickname() {
        // 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user1");
        // 비밀번호에 닉네임이 포함된 경우
        JoinRequestDto request6 = new JoinRequestDto("user6", "user212", "user212", "user2");
        JoinRequestDto request7 = new JoinRequestDto("user7", "user312", "user312", "user3");

        // Exception Test
        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));
        Assertions.assertThatIllegalArgumentException()
              .isThrownBy(() -> memberService.join(request6))
              .withMessage("비밀번호에 닉네임이 포함되어서는 안됩니다.");
        Assertions.assertThatIllegalArgumentException()
              .isThrownBy(() -> memberService.join(request7))
              .withMessage("비밀번호에 닉네임이 포함되어서는 안됩니다.");
    }

    @DisplayName("비밀번호와 비밀번호 재확인이 다른 경우")
    @Test
    void wrongPasswordCheckPassword() {
        // 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");

        // 비밀번호가 다름
        JoinRequestDto request8 = new JoinRequestDto("user8", "1111", "1112", "user1");
        JoinRequestDto request9 = new JoinRequestDto("user9", "1111", "1112", "user2");

        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));

        // Exception Test
        Assertions.assertThatIllegalArgumentException()
              .isThrownBy(() -> memberService.join(request8))
              .withMessage("비밀번호와 비밀번호 재확인은 일치해야 합니다.");
        Assertions.assertThatIllegalArgumentException()
              .isThrownBy(() -> memberService.join(request9))
              .withMessage("비밀번호와 비밀번호 재확인은 일치해야 합니다.");
    }

    @DisplayName("중복되는 닉네임일 경우")
    @Test
    void duplicatedNickname() {
        // 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");
        JoinRequestDto request2 = new JoinRequestDto("user2", "1111", "1111", "user");

        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));

        // Exception Test
        Assertions.assertThatIllegalArgumentException()
              .isThrownBy(() -> memberService.join(request2))
              .withMessage("중복되는 닉네임 입니다.");
    }
}
