package com.example.wpdelhi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;

/**
 * Disambiguation: https://www.baeldung.com/spring-qualifier-annotation
 * Application context will contain
 * Map of string and bean
 *  restTemplateConfig --->Bean of RestTemplateConfig
 *  restTemplate --->$$restTemplate
 */
@Configuration
public class RestTemplateConfig {

    @Bean("kumar")
    @Scope("prototype")
    public RestTemplate restTemplate(){
        System.out.println("Inside restTemplate");
        return new RestTemplate();
    }

    @Bean("vinay")
    public RestTemplate restTemplate2(){
        System.out.println(" ----------- inside restTemplate2 ");
        return new RestTemplate();
    }
}
