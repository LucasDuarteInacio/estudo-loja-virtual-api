package com.lucasduarte.lojavirtual.config;

import com.lucasduarte.lojavirtual.service.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBService service;

    @Bean
    public Boolean instantiateDataBase() throws ParseException {
        service.instantiateTestDatabase();
        return true;

    }

}
