package com.sasho.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.sasho.hibernate.repos.AuthorityRepo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
@RequiredArgsConstructor
@OpenAPIDefinition(security = {@SecurityRequirement(name = "bearer-key")})
public class InitialConfiguration implements CommandLineRunner {
    private final AuthorityRepo authorityRepo;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.registerModule(hibernate5Module);
        return mapper;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        License license = new License()
                .name("Apache 2.0")
                .url("http://springdoc.org");
        Info info = new Info()
                .title("BackEnd API")
                .version("1.0")
                .description("Back end api with JWT security example")
                .termsOfService("http://swagger.io/terms/")
                .license(license);
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
        Components components = new Components().addSecuritySchemes("bearer-key", securityScheme);
        return new OpenAPI().components(components).info(info);
    }

    @Override
    public void run(String... args) throws Exception {
//        var a = Authority.builder().authority("ROLE_USER").build();
//        var b = Authority.builder().authority("ROLE_ADMIN").build();
//        this.authorityRepo.saveAll(Set.of(a, b));

    }
}
