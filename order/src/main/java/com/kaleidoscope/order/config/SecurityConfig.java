package com.kaleidoscope.order.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig implements WebFluxConfigurer {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkUrl;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable) // Disable CSRF if not required
                .authorizeExchange(exchanges -> exchanges
//                                .pathMatchers("/api/v1/inventory/getinventoryitems").permitAll()
//                        .pathMatchers(AUTH_WHITELIST).permitAll()
                        .pathMatchers("/api/auth/**", "/api/**").permitAll()
//                        .pathMatchers(HttpMethod.GET, "/api/v1/product", "/api/v1/product/{productId}", "/api/v1/product/categories", "/api/v1/product/search").permitAll()
//                        .pathMatchers(HttpMethod.POST, "/api/v1/product").hasRole("ADMIN")
//                        .pathMatchers(HttpMethod.PUT, "/api/v1/product/{productId}").hasRole("ADMIN")
//                        .pathMatchers(HttpMethod.DELETE, "/api/v1/product/{productId}").hasRole("ADMIN")
//                        .pathMatchers("/api/inventory/**").hasAnyRole("ADMIN")
//                        .pathMatchers("/api/order/**").hasAnyRole("USER")
//                                .pathMatchers("/api/v1/product/").hasRole("kalei_ADMIN")
//                                .pathMatchers("/api/v1/order/getallorders").hasRole("kalei_CLIENT")
//
//                             .anyExchange().authenticated()
                )

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );


        return http.build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        String jwkSetUri = jwkUrl;
        NimbusReactiveJwtDecoder decoder = NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).build();

        return token -> decoder.decode(token)
                .doOnSuccess(jwt -> log.info("Decoded JWT Token: {}", jwt.getClaims()))
                .doOnError(error -> log.error("Failed to decode JWT Token: ", error));
    }

    private ReactiveJwtAuthenticationConverterAdapter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Object resourceAccess = jwt.getClaims().get("resource_access");
            if (resourceAccess instanceof Map) {
                Map<String, Object> resourceAccessMap = (Map<String, Object>) resourceAccess;
                Object reactFrontend = resourceAccessMap.get("react-frontend");
                if (reactFrontend instanceof Map) {
                    Map<String, Object> reactFrontendMap = (Map<String, Object>) reactFrontend;
                    Object roles = reactFrontendMap.get("roles");
                    if (roles instanceof Collection) {
                        return ((Collection<String>) roles).stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(Collectors.toList());
                    }
                }
            }
            return Collections.emptyList();
        });

        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }






}
