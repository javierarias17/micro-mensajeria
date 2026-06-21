package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.infrastructure.security.JwtAccessDeniedHandler;
import com.pragma.powerup.infrastructure.security.JwtAuthenticationEntryPoint;
import com.pragma.powerup.infrastructure.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String ENDPOINT_SMS = "/api/v1/sms";

    private static final String SWAGGER_API_DOCS_PATH = "/v3/api-docs/**";
    private static final String SWAGGER_UI_PATH = "/swagger-ui/**";
    private static final String SWAGGER_HTML_PATH = "/swagger-ui.html";

    private static final String ROLE_EMPLOYEE = "EMPLOYEE";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .authorizeRequests()
                .antMatchers(SWAGGER_API_DOCS_PATH, SWAGGER_UI_PATH, SWAGGER_HTML_PATH).permitAll()
                .antMatchers(HttpMethod.POST, ENDPOINT_SMS).hasRole(ROLE_EMPLOYEE)
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
