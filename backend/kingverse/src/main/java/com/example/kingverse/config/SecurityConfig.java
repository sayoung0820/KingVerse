package com.example.kingverse.config;

import com.example.kingverse.service.DbUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider daoAuthProvider(DbUserDetailsService uds, PasswordEncoder encoder) {
        var p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(encoder);
        return p;
    }

//    @Bean
//    SecurityFilterChain security(HttpSecurity http,
//                                 DaoAuthenticationProvider daoAuthProvider) throws Exception {
//        http
//                .authenticationProvider(daoAuthProvider)
//                .csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/", "/index.html", "/assets/**", "/images/**").permitAll()
//                        .requestMatchers("/api/ping", "/api/debug/**").permitAll()
//                        .requestMatchers(org.springframework.http.HttpMethod.GET,
//                                "/api/books/**", "/api/characters/**").permitAll()
//                        .requestMatchers("/api/auth/me").permitAll()
//                        .anyRequest().authenticated()
//                )
//
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .logout(Customizer.withDefaults());
//        return http.build();
//    }


    @Bean
    SecurityFilterChain security(HttpSecurity http,
                                 DaoAuthenticationProvider daoAuthProvider) throws Exception {
        http
                .authenticationProvider(daoAuthProvider)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/assets/**", "/images/**").permitAll()
                        .requestMatchers("/api/ping", "/api/debug/**").permitAll()
                        .requestMatchers(org.springframework.http.HttpMethod.GET,
                                "/api/books/**", "/api/characters/**").permitAll()
                        .requestMatchers("/api/auth/me").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        // ← no redirect; OK for XHR:
                        .successHandler((req, res, auth) -> res.setStatus(200))
                        .failureHandler((req, res, ex) -> res.sendError(401))
                )
                .logout(logout -> logout
                        // ← also don’t redirect on logout:
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                );
        return http.build();
    }

}
