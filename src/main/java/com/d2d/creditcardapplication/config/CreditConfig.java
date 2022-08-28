package com.d2d.creditcardapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CreditConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
