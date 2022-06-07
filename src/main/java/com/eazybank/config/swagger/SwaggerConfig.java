package com.eazybank.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig {

    private static  final String AUTHORIZATION_HEADER = "Authorization";
    private ApiKey apikey(){
        return  new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.eazybank"))
                .build()
                .apiInfo(apiDetails())
                .securitySchemes(List.of(apikey()))
                .securityContexts(List.of(securityContext()));
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Eazy Bank",
                "eazybank Api",
                "1.0",
                "this version is free to all",
                new springfox.documentation.service.Contact("Aikay Groups",
                        "https://aikaygroup.io",
                        "ucheyzecx@gmail.com"),
                "Api License",
                "https://aikaygroup.io",
                Collections.emptyList()
        );
    }
    private SecurityContext securityContext(){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "totalAccess");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;

        return List.of(new SecurityReference("JWT", authorizationScopes));
    }
}
