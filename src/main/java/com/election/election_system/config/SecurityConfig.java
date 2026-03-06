package com.election.election_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

 @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) 
            .headers(headers -> headers.frameOptions(frame -> frame.disable())); 

        return http.build();
    }
/*@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for H2 console
        .headers(headers -> headers.frameOptions(frame -> frame.disable())) // <-- Must allow frames
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/h2-console/**").permitAll()
            .requestMatchers("/admin.html").permitAll()
            .requestMatchers("/user.html").permitAll()
            .requestMatchers("/admin/**").hasAuthority("ADMIN")
            .requestMatchers("/counter/**").hasAuthority("COUNTER")
            .requestMatchers("/user/**").hasAuthority("USER")
            .anyRequest().authenticated()
        )
         //.formLogin(Customizer.withDefaults()); 
        .httpBasic(Customizer.withDefaults());

    return http.build();
}*/

}