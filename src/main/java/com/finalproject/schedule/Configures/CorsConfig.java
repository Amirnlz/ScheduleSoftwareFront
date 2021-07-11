package com.finalproject.schedule.Configures;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer cors() {

        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                registry.addMapping("/**").
                        allowedMethods("POST", "GET", "PUT", "DELETE").
                        allowedHeaders("*").
                        allowedOrigins("http://localhost:3000");
            }
        };
    }
}
