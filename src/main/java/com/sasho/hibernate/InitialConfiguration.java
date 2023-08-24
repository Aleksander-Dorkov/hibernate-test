package com.sasho.hibernate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.sasho.hibernate.domain.Authority;
import com.sasho.hibernate.repos.AuthorityRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Set;


@Configuration
@RequiredArgsConstructor
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

    @Override
    public void run(String... args) throws Exception {
//        var a = Authority.builder().authority("ROLE_USER").build();
//        var b = Authority.builder().authority("ROLE_ADMIN").build();
//        this.authorityRepo.saveAll(Set.of(a, b));

    }
}
