package com.example.demo.config.auth;

import com.example.demo.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션들을 disable한다.
                .and()
                    .authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점이다 / authorizeRequests가 선언되어야만 antMatchers 옵션을 사용할 수 있다.
                    .antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) // 권한 관리 대상을 지정하는 옵션
                    .anyRequest().authenticated() // 설정된 값들 이외 나머지 URL들을 나타낸다. 여기서는 나머지는 모두 인증된 사용자들(로그인한사용자)에게만 허용하게함
                .and()
                    .logout()
                        .logoutSuccessUrl("/") // 로그아웃 기능에 대한 여러설정의 진입점, 로그아웃 성공시 / 주소로 이동
                .and()
                    .oauth2Login() // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                        .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정담당
                            .userService(customOauth2UserService); // 소셜 로그인 성공시 후속 조치를 진행한 UserService 인터페이스의 구현체를 등록한다
        // 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있다.
    }
}
