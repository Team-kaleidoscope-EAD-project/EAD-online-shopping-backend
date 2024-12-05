package com.kaleidoscope.inventory.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.WebFluxConfigurer;

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
                                .pathMatchers("/api/v1/inventory/getinventoryitems","/api/v1/inventory/getinventoryitem/{sku}","/api/v1/inventory/test").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/inventory/addinventoryitem").hasRole("kalei_ADMIN")
                                .pathMatchers(HttpMethod.PUT, "/api/v1/inventory/updateinventoryitem").hasRole("kalei_ADMIN")
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/inventory/deleteinventoryitem/{sku}").hasRole("kalei_ADMIN")
                                .anyExchange().authenticated()
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
