package ru.mtuci.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .authorizeHttpRequests((auth) ->auth
                .requestMatchers("/main").hasAnyRole("User","Admin")
                        .requestMatchers("/main").hasAnyRole("User","Admin")
                        .requestMatchers("/main").hasRole("Admin")
                        .requestMatchers("/main").hasRole("Admin")
                        .requestMatchers("/main").hasRole("Admin")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        return new InMemoryUserDetailsManager(User.builder()
                .username("User")
                .password(passwordEncoder().encode("password"))
                .roles("User")
                .build(),
                User.builder()
                        .username("Admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("Admin")
                        .build());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
