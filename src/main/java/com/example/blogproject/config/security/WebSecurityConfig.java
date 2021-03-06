package com.example.blogproject.config.security;

import com.example.blogproject.config.security.filter.FormLoginFilter;
import com.example.blogproject.config.security.filter.JwtAuthFilter;
import com.example.blogproject.config.security.jwt.HeaderTokenExtractor;
import com.example.blogproject.config.security.provider.FormLoginAuthProvider;
import com.example.blogproject.config.security.provider.JWTAuthProvider;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTAuthProvider jwtAuthProvider;
    private final HeaderTokenExtractor headerTokenExtractor;

    public WebSecurityConfig(
          JWTAuthProvider jwtAuthProvider,
          HeaderTokenExtractor headerTokenExtractor
    ) {
        this.jwtAuthProvider = jwtAuthProvider;
        this.headerTokenExtractor = headerTokenExtractor;
    }

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth
              .authenticationProvider(formLoginAuthProvider())
              .authenticationProvider(jwtAuthProvider);
    }

    @Override
    public void configure(WebSecurity web) {
        // h2-console ????????? ?????? ?????? (CSRF, FrameOptions ??????)
        web
              .ignoring()
              .antMatchers("/h2-console/**");

        web.ignoring()
              .requestMatchers(PathRequest.toStaticResources().atCommonLocations());

        web.ignoring()
              .antMatchers("/**/api-docs",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**",
                    "/v3/api-docs/**",
                    "/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // ???????????? ????????? JWT??? ???????????? ????????? Session??? ????????? ????????????.
        http
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
              .antMatchers("/v1/**").permitAll()
              .anyRequest().permitAll()
              .and()
              // [???????????? ??????]
              .logout()
              // ???????????? ?????? ?????? URL
              .logoutUrl("/user/logout")
              .permitAll()
              .and()
              .exceptionHandling()
              // "?????? ??????" ????????? URL ??????
              .accessDeniedPage("/forbidden.html");

        /*
         * 1.
         * UsernamePasswordAuthenticationFilter ????????? FormLoginFilter, JwtFilter ??? ???????????????.
         * FormLoginFilter : ????????? ????????? ???????????????.
         * JwtFilter       : ????????? ????????? JWT ?????? ??? ????????? ???????????????.
         */
        http
              .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter formLoginFilter = new FormLoginFilter(authenticationManager());
        formLoginFilter.setFilterProcessesUrl("/user/login");
        formLoginFilter.setAuthenticationSuccessHandler(formLoginSuccessHandler());
        formLoginFilter.afterPropertiesSet();
        return formLoginFilter;
    }

    @Bean
    public FormLoginSuccessHandler formLoginSuccessHandler() {
        return new FormLoginSuccessHandler();
    }

    @Bean
    public FormLoginAuthProvider formLoginAuthProvider() {
        return new FormLoginAuthProvider(encodePassword());
    }

    private JwtAuthFilter jwtFilter() throws Exception {
        List<String> skipPathList = new ArrayList<>();

        // Static ?????? ?????? ??????
        skipPathList.add("GET,/images/**");
        skipPathList.add("GET,/css/**");
        skipPathList.add("GET,/css/**");

        // h2-console ??????
        skipPathList.add("GET,/h2-console/**");
        skipPathList.add("POST,/h2-console/**");
        // ?????? ?????? API ??????
        skipPathList.add("GET,/user/**");
        skipPathList.add("POST,/user/signup");

        skipPathList.add("GET,/");
        skipPathList.add("GET,/basic.js");

        skipPathList.add("GET,/favicon.ico");

        // swagger
        skipPathList.add("GET, /api/v2/**");
        skipPathList.add("GET, /health");
        skipPathList.add("GET, /swagger-ui.html");
        skipPathList.add("GET, /swagger/**");
        skipPathList.add("GET, /swagger-resources/**");
        skipPathList.add("GET, /webjars/**");
        skipPathList.add("GET, /v2/api-docs");

        FilterSkipMatcher matcher = new FilterSkipMatcher(
              skipPathList,
              "/**"
        );

        JwtAuthFilter filter = new JwtAuthFilter(
              matcher,
              headerTokenExtractor
        );
        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}