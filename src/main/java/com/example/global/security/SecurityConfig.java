package com.example.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.addFilterBefore(
                jwtAuthFilter
                , CorsFilter.class
        );
        http
                .cors()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers("/users/signup", "/users/login").permitAll()
                .antMatchers("/users/**").authenticated()
                .antMatchers(HttpMethod.PATCH, "/users/{id}").access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/movies").permitAll()
                .antMatchers("/movies/**").access("hasRole('ROLE_ADMIN')")

                .antMatchers(HttpMethod.GET, "/seats").permitAll()
                .antMatchers(HttpMethod.POST, "/seats").access("hasRole('ROLE_ADMIN')")

                .antMatchers("/theaters").permitAll()
                .antMatchers("/theaters/**").permitAll()
                .antMatchers(HttpMethod.POST, "/theaters").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.PATCH, "/theaters").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/theaters").access("hasRole('ROLE_ADMIN')")

                .antMatchers("/schedules/**").permitAll()
                .antMatchers(HttpMethod.POST, "/schedules").access("hasRole('ROLE_ADMIN')")
                .antMatchers(HttpMethod.DELETE, "/schedules").access("hasRole('ROLE_ADMIN')")

                .antMatchers("/payments").authenticated()
                .antMatchers("/payments/**").authenticated()



        ;
        return http.build();
    }

}
