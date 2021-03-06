package com.example.blogproject.config.security;

import com.example.blogproject.config.security.jwt.JwtTokenUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "BEARER";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final MemberDetailsImpl userDetails = ((MemberDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails);
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);

        ResponseCookie cookie = ResponseCookie.from("accessToken", TOKEN_TYPE + "+" + token)
              .maxAge(7 * 24 * 60 * 3)
              .path("/")
              .secure(true)
              .sameSite("None")
              .httpOnly(true)
              .build();
        response.setHeader("Set-Cookie", cookie.toString());

    }

}
