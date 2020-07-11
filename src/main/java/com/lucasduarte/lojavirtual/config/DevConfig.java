package com.lucasduarte.lojavirtual.config;

import com.lucasduarte.lojavirtual.service.DBService;
import com.lucasduarte.lojavirtual.service.EmailService;
import com.lucasduarte.lojavirtual.service.SmtpMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.validation.constraints.Email;
import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    private DBService service;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public Boolean instantiateDataBase() throws ParseException {
        if(!"create".equals(strategy)){
          return false;
        }

        service.instantiateTestDatabase();
        return true;

    }

    @Bean
    public EmailService emailService(){
        return new SmtpMailService();
    }

}
