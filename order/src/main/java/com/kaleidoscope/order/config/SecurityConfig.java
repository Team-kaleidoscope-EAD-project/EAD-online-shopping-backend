package com.kaleidoscope.order.config;

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
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                                // Order controller
                                .pathMatchers("/api/v1/order/test","/api/v1/order/test","/api/v1/product/{id}","/api/v1/product/sku/{sku}","/api/v1/product/filter").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/publish","/api/v1/order/addorder").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/order/updateOrder").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/order/deleteorder/{orderId}").permitAll()
                                .pathMatchers(HttpMethod.GET, "/api/v1/order/getallorders").hasRole("kalei_ADMIN")

                                // Order item controller
                                .pathMatchers("/api/v1/order/getallorderitem","api/v1/order/getallorderitemsbyorderid/{orderId}").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/addorderitems").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/order/updateorderitem").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/order/deletorderitem/{itemId}").permitAll()

                                // Payment controller
                                .pathMatchers("/api/v1/order/getallpayments","api/v1/order/getpaymentbyorderid/{orderId}").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/addpayment").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/order/updatepayment").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/order/deletepayment/{paymentId}").permitAll()

                                // Place order controller
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/addplaceorder","/api/v1/order/confirmorder").permitAll()

                                // Shipping controller
                                .pathMatchers("/api/v1/order/getallshippings","api/v1/order/getshippingbyorderid/{orderId}").permitAll()
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/addshipping").permitAll()
                                .pathMatchers(HttpMethod.PUT, "/api/v1/order/updateshipping").permitAll()
                                .pathMatchers(HttpMethod.DELETE, "/api/v1/order/deleteshipping/{shippingId}").permitAll()

                                // Stripe payment controller
                                .pathMatchers(HttpMethod.POST, "/api/v1/order/stripepayment/create-payment-intent").permitAll()


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
