//package config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    /**
//     * Configures the security filter chain for handling HTTP security.
//     *
//     * @param serverHttpSecurity the HTTP security configuration for the server
//     * @return SecurityWebFilterChain configuration object
//     */
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
//        serverHttpSecurity
//                .csrf().disable() // Disable CSRF protection as it's not required for this configuration
//                .authorizeExchange(exchange -> exchange
//                        .pathMatchers("/eureka/**").permitAll() // Allow unauthenticated access to the /eureka endpoint
//                        .anyExchange().authenticated() // Require authentication for all other endpoints
//                )
//                .oauth2ResourceServer(ServerHttpSecurity.OAuth2ResourceServerSpec::jwt); // Enable JWT-based OAuth2 authentication
//
//        return serverHttpSecurity.build(); // Build and return the configured filter chain
//    }
//}
