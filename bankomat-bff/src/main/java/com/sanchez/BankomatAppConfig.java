package com.sanchez;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration(proxyBeanMethods = false)
@EnableAutoConfiguration
@EnableScheduling
@Slf4j
public class BankomatAppConfig extends SpringBootServletInitializer {

    @Bean
    public WebMvcConfigurer corsConfigurer() {

        String[] allowDomains = new String[1];
        allowDomains[0] = "http://localhost:4200";

        log.info("Starting with enabled CORS for domains: {}", allowDomains);

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "PATCH");
                registry.addMapping("/**").allowedOrigins(allowDomains);
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BankomatApp.class);
    }
}
