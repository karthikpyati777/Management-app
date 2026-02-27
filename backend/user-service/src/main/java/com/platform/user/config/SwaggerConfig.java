package com.platform.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI userServiceAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Service API")
                .description("REST API for User Management")
                .version("1.0"));
    }
}
