package com.koscom.springboot.config.auth;

import com.koscom.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // security 기능 활성화 시킬 것임을 나타냄
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Value("${security.enabled:true}")
    private boolean securityEnabled;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                        .antMatchers("/","/css/**","/images/**", "/js/**", "/h2-console/**").permitAll() // 인증없이 사용가능한 항목
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())  // 유저 Role 가지고 있어야 사용가능 : <인가>
                        .anyRequest().authenticated()  // 이외 case는 인증만 되면 가능, <인증>
                .and()
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 성공시 index로 페이지 이동
                .and()
                    .oauth2Login()
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService); // 로그인 시도 후 유저정보 가지고 와서 어떤 서비스를 실행할 것인지

    }
    //위 까지가 스프링 시큐리티 설정

    //테스트에서 사용할 경우 security 무시
    // security.enabled의 디폴트 값은 true 이나,
    // false로 설정되는 경우에는 위의 스프링 시큐리티 설정을 무시하고 진행하겠다는 것
    @Override
    public void configure(WebSecurity web) throws Exception {
        if(!securityEnabled){
            web.ignoring().antMatchers("/**");
        }
    }
}
