package com.everis.bootcoin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@EnableEurekaClient
@SpringBootApplication
public class BootcoinApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootcoinApplication.class, args);
    }

    @Bean
    public WebClient.Builder getWebClient(){
        return WebClient.builder();
    }

}
