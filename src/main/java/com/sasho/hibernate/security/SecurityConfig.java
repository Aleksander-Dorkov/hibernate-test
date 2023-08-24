package com.sasho.hibernate.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JWTService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .httpBasic()
                .disable()
                .headers()
                .and()
                .csrf()
                .csrfTokenRepository(getCsrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/users/register", "/users/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new JwtFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CsrfTokenRepository getCsrfTokenRepository() {
        CookieCsrfTokenRepository tokenRepository = CookieCsrfTokenRepository.withHttpOnlyFalse();
        tokenRepository.setHeaderName("X-XSRF-TOKEN");
        return tokenRepository;
    }
}
