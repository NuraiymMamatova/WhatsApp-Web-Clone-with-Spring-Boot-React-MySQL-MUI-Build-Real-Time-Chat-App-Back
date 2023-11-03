package com.whatsappyoutube.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .cors(cors -> cors.configurationSource(request -> {
//                    CorsConfiguration corsConfig = new CorsConfiguration();
//                    corsConfig.addAllowedHeader("*");
//                    corsConfig.addAllowedMethod("*");
//                    corsConfig.addAllowedOrigin("http://localhost:3000");
//                    return corsConfig;
//                }))
//                .authorizeHttpRequests((authorizeHttpRequests) ->
//                        authorizeHttpRequests
//                                .requestMatchers(
//                                        "/",
//                                        "/api/auth/**",
//                                        "/swagger-ui/**",
//                                        "v3/api-docs/**",
//                                        "/api/admin/**")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated())
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//                .authenticationProvider(authenticationProvider()).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement((sessionManagment) -> sessionManagment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                    .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).
                csrf(AbstractHttpConfigurer::disable).cors((cors) -> {
                    cors.configurationSource(new CorsConfigurationSource() {
                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000/"));
                            corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
                            corsConfiguration.setAllowCredentials(true);
                            corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
                            corsConfiguration.setExposedHeaders(List.of("Authorization"));
                            corsConfiguration.setMaxAge(3600L);
                            return corsConfiguration;
                        }
                    });
                }).formLogin(AbstractAuthenticationFilterConfigurer::permitAll).httpBasic(Customizer.withDefaults());
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

/*.formLogin(formLogin ->
    formLogin
        .loginPage("/login")
        .permitAll()
)*/