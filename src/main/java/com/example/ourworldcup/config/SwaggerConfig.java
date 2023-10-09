package com.example.ourworldcup.config;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String API_NAME = "OUR WORLDCUP API";
    private static final String API_VERSION = "1.0";
    private static final String API_DESCRIPTION = "OUR WORLDCUP API 명세서";
    private static final String GROUP_NAME = "v1.0-definition";

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(GROUP_NAME)
                .packagesToScan("com.example.ourworldcup.controller")
                .build();
    }

    @Bean
    public OpenAPI ourworldcupAPI() {
        Info info = new Info()
                .title(API_NAME)
                .description(API_DESCRIPTION)
                .version(API_VERSION);

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }

}
