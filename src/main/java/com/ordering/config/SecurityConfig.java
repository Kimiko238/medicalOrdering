package com.ordering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // ★HTTPリクエストに対するセキュリティ設定
        .authorizeHttpRequests(authorize -> authorize
                //下記は指定したもので遷移した際に認証をしないでパスする設定
                .requestMatchers("/login", "/favicon.ico", "/userRegister", "/createUser")
                .permitAll()
//            その他の設定：認証が必要と設定している
                .anyRequest().authenticated()
        )
//        ログイン時の設定
        .formLogin(formLogin -> formLogin
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("name")
            .passwordParameter("pass")
            .failureForwardUrl("/login")
            .defaultSuccessUrl("/", true)
        )
        //        ログアウト時の設定
        .logout(logout -> logout
            .logoutSuccessUrl("/login")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        )
        .exceptionHandling(configurer -> configurer.authenticationEntryPoint(
            new LoginUrlAuthenticationEntryPoint("/login"))
        )
    ;
    return http.build();
  }
}
