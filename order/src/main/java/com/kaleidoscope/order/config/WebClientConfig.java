package com.kaleidoscope.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient inventoryWebClient() {
        return webClientBuilder().baseUrl("http://apigateway/api/v1/inventory").build();
    }

    @Bean
    public WebClient productWebClient() {
        return webClientBuilder().baseUrl("http://apigateway/api/v1/product").build();
    }
}
