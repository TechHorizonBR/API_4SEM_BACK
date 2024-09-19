package br.fatec.bd4.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.geom.PathIterator;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI(){
        return new OpenAPI()
                .info(new Info().title("LocalTracker API")
                        .description("Aplicação Back-end responsável por restaurar e gravar dados no banco de dados.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }



}