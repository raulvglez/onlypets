package com.example.fb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("!https")
public class SecurityConfig {

    private UserService userService;

    @Bean
    public void filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/index*", "/static/**", "/*.js", "/*.json", "/*.ico")
                .permitAll()
                .anyRequest().authenticated();

        http.formLogin().loginPage("/index.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/main.html", true)
                .failureUrl("/index.html?error=true")
                .usernameParameter("username").passwordParameter("password");
        http.logout()
                .permitAll()
                .logoutSuccessUrl("/index.html?logout");
    }

    //Crea el encriptador de contraseÃ±as

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    public void configureGlobalLogin(AuthenticationManagerBuilder authentication) {
        authentication.userService
    }

}
