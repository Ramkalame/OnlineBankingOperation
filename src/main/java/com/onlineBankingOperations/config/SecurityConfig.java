package com.onlineBankingOperations.config;

import com.onlineBankingOperations.config.filter.CsrfCookieFilter;
import com.onlineBankingOperations.config.filter.JWTTokenGenerateFilter;
import com.onlineBankingOperations.config.filter.JWTTokenValidateFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        return http
                .sessionManagement(sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//we don't want to create session for the user after login for the request the spring security don't have idea about the previous request
                .cors(corsConfig->corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cors = new CorsConfiguration();
                        cors.setAllowedOrigins(Collections.singletonList("*"));
                        cors.setAllowedMethods(Collections.singletonList("*"));
                        cors.setAllowCredentials(true);
                        cors.setAllowedHeaders(Collections.singletonList("*"));
                        cors.setExposedHeaders(Arrays.asList("AUTHORIZATION"));
                        cors.setMaxAge(3600L);
                        return cors;
                    }
                }))
                .csrf(csrfConfig->csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))//using this configuration we can store cookie value
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)//my CsrfCookieFilter execute after the successfully login the user and generate the csrf token
                .addFilterAfter(new JWTTokenGenerateFilter(), BasicAuthenticationFilter.class)//this filter execute after the successfully login the user
                .addFilterBefore(new JWTTokenValidateFilter(), BasicAuthenticationFilter.class)// this filter execute before the actual authentication process which is basic authentication filter
                .authorizeHttpRequests((request)-> request
                .requestMatchers("/client/register").permitAll()
                .anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults())
        .httpBasic(Customizer.withDefaults())
        .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
