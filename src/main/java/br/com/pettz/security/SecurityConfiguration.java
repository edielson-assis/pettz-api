package br.com.pettz.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration implements WebMvcConfigurer {

    private final TokenService tokenService; 

    private static final String AUTHORITY_NAME = "Admin";
    private static final String PUBLIC_POST_METHODS = "/api/v1/auth/**";
    private static final String[] SWAGGER = {"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**"};
    private static final String[] PUBLIC_GET_METHODS = {"/api/v1/categories/**", "/api/v1/products/**", "/api/v1/auth/refresh/**"};
    private static final String[] ADMIN_METHODS = {"/api/v1/categories/admin/**", "/api/v1/products/admin/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        SecurityFilter securityFilte = new SecurityFilter(tokenService);

        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilte, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(HttpMethod.POST, PUBLIC_POST_METHODS).permitAll()
                    .requestMatchers(HttpMethod.GET, PUBLIC_GET_METHODS).permitAll()
                    .requestMatchers(SWAGGER).permitAll()
                    .requestMatchers(ADMIN_METHODS).hasAuthority(AUTHORITY_NAME)
                    .requestMatchers("/admin/**").hasAuthority(AUTHORITY_NAME)
                    .requestMatchers("/users").denyAll();
                    req.anyRequest().authenticated();
                }).cors(Customizer.withDefaults()).build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}