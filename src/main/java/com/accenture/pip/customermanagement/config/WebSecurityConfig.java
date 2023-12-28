package com.accenture.pip.customermanagement.config;

import jakarta.ws.rs.HttpMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

  /*  @Value("${spring.security.oauth2.resourceserver.opaque.introspection-uri}")
    String introspectionUri;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-id}")
    String clientId;

    @Value("${spring.security.oauth2.resourceserver.opaque.introspection-client-secret}")
    String clientSecret;
*/

    public static final String ROOT_URI = "/customer";

    private static final String[] AUTH_WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**"
    };

    public static final String[] ROLES = {"admin", "manager", "manage-account", "manage-account-links", "view-profile"};

  /*  @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/version").permitAll()
                                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                           *//*     .requestMatchers(HttpMethod.GET, ROOT_URI).permitAll()
                                .requestMatchers(HttpMethod.POST, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.DELETE, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.PUT, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.PATCH, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(AUTH_WHITE_LIST).permitAll()*//*
                                .requestMatchers(ROOT_URI).authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.opaqueToken
                        (token -> token.introspectionUri(this.introspectionUri)
                                .introspectionClientCredentials(this.clientId, this.clientSecret)))
                //.oauth2ResourceServer(oauth2 -> oauth2.opaqueToken(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();

    }*/

    @Bean
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request
                                //.requestMatchers(HttpMethod.POST, "/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/version").permitAll()
                                .requestMatchers(HttpMethod.GET, "/health").permitAll()
                                .requestMatchers(HttpMethod.GET, ROOT_URI).permitAll()
                                .requestMatchers(HttpMethod.POST, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.DELETE, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.PUT, ROOT_URI).hasAnyRole(ROLES)
                                .requestMatchers(HttpMethod.PATCH, ROOT_URI).hasAnyRole(ROLES)
                                .anyRequest().authenticated())
                .oauth2ResourceServer(token -> token.jwt(Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter authenticationConverter() {
        JwtAuthenticationConverter authenticationConverter = new JwtAuthenticationConverter();
        JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
        authoritiesConverter.setAuthorityPrefix("");
        authoritiesConverter.setAuthoritiesClaimName("roles");
        authenticationConverter.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
        return authenticationConverter;
    }

}
