package ru.mtuci.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf((csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                //.authorizeHttpRequests((auth) ->auth
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/registration", "/autorisation").permitAll()
                        .anyRequest().authenticated()
                )
//                .requestMatchers("/main").hasAnyRole("User","Admin")
//                        .requestMatchers("/main").hasAnyRole("User","Admin")
//                        .requestMatchers("/main").hasRole("Admin")
//                        .requestMatchers("/main").hasRole("Admin")
//                        .requestMatchers("/main").hasRole("Admin")
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults());
                //.formLogin(form -> form
                //        .loginPage("/registration").permitAll()
                //        .defaultSuccessUrl("/main", true))
                .formLogin(form -> form
                        .loginPage("/autorisation").permitAll()
                        .defaultSuccessUrl("/main", true))
                .logout(form -> form
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")).permitAll()
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "XSRF-TOKEN"));

    return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        return new InMemoryUserDetailsManager(User.builder()
//                .username("User")
//                .password(passwordEncoder().encode("password"))
//                .roles("User")
//                .build(),
//                User.builder()
//                        .username("Admin")
//                        .password(passwordEncoder().encode("admin"))
//                            .roles("Admin")
//                        .build());
//    }

    @Bean
    public AuthenticationManager daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
