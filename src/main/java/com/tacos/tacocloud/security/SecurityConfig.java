package com.tacos.tacocloud.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { //HTTP 보안을 구성하는 메소드

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override // 웹 요청 보안 처리 , 타코 디자인하거나 주문하기 전에 사용자를 인증해야한다는 것이 타코 요구사항, 홈페이지,로그인 페이지, 등록페이지는 인증 x (모든 사용자 사용 가능)
    protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests() // 요청에 대한 인증을 설정한다.
                .antMatchers("/design", "/orders")//"/design"과 "/orders" 경로에 대한 요청을 선택한다
                .access("hasRole('ROLE_USER')")//"/design"과 "/orders"에 대한 요청은 "ROLE_USER" 역할을 가진 사용자만 접근할 수 있다.
                .antMatchers("/", "/**")
                .access("permitAll()") // "/"와 그 이외의 모든 경로에 대한 요청은 모든 사용자에게 허용된다.
                //antMatchers의 위치를 바꾸면 design,orders의 요청 효력 없어진다.

                .and()
                .formLogin()
                .loginPage("/login")
                    .defaultSuccessUrl("/design", true) // 로그인 전에 어떤 페이지에 있었던 로그인 성공시 /design 페이지로 이동
                    .failureUrl("/login?error=true")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")

                .and()
                    .csrf();
    }



    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { //사용자 인증 정보를 구성하는 메소드
        // 커스텀 사용자 명세 서비스
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
        // 인메모리 기반 사용자 스토어 예제
//        auth.inMemoryAuthentication()
//                .withUser("user1")
//                .password("{noop}password1")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("user2")
//                .password("{noop}password2")
//                .authorities("ROLE_USER");

//        //jdbc 기반의 사용자 스토어
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from users" +
//                                "where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from authorities" +
//                                "where username=?"
//                )
//                .passwordEncoder(new BCryptPasswordEncoder());

    }
}
