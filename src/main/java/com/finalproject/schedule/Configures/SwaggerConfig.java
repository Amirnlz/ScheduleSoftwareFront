package com.finalproject.schedule.Configures;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.finalproject.schedule"))
                .paths(PathSelectors.ant("/api/**"))
                .build().apiInfo(apiDetails());
        return  docket;
    }

    private ApiInfo apiDetails() {

        return  new ApiInfo(
                " سیستم مدیریت امور دانشگاهی",
                "پیاده سازی سیستم مدیریت امور دانشگاهی",
                "1.0",
                "آزاد برای همه",
                new springfox.documentation.service.Contact("بهزاد-حسین-امیر-فردین","#","#"),
                "API Licanse",
                "#",
                Collections.emptyList()
        );
    }

//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
}
