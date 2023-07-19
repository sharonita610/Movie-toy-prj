package com.example.global.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
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
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

//     extends WebSecurityConfigurerAdapter
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.httpBasic().disable();
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
//                .csrf().disable()
                .httpBasic().disable()
////                .sessionManagement()
//////                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//////                .and()
                .authorizeRequests()
//////                .antMatchers(HttpMethod.GET, "/movies").permitAll()
//////                .antMatchers("/schedules").permitAll()
//////                .antMatchers("/users").permitAll()
//////                .antMatchers(HttpMethod.DELETE, "/schedules").authenticated()
//////                .antMatchers("/payments").authenticated()
//////                .antMatchers("/").authenticated()
                .anyRequest().permitAll();
//
                http.addFilterAfter(jwtAuthFilter, CorsFilter.class);

        return http.build();

    }
}
