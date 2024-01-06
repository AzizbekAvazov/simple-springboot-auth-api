package com.azizbek.fancybackservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Creator: Azizbek Avazov
 * Date: 01.07.2022
 * Time: 13:45
 */

@Configuration
//@CrossOrigin
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // local http://localhost:3000
                .allowCredentials(true);
    }

}
