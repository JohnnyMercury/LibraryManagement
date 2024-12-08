package com.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // Определяем PasswordEncoder как бин
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Настройка SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем CSRF для простоты (не рекомендуется для продакшена)
                .csrf().disable()

                // Настраиваем авторизацию запросов
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/users/register", "/users/login", "/h2-console/**", "/books/add", "/books/all", "/books/delete/{bookId}", "/books/search").permitAll()
                        .anyRequest().authenticated()
                )


                // Настраиваем сессию как stateless (если используете JWT или другой подход)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.STATELESS)
                )

                // Настраиваем HTTP Basic аутентификацию (можно заменить на другой механизм)
                .httpBasic();

        // Разрешаем доступ к H2 консоли (если используется)
        http.headers(headers -> headers.frameOptions().sameOrigin());

        return http.build();
    }

    // Опционально: определяем AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
