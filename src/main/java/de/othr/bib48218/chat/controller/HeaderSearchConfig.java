package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.HeaderSearchElementFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeaderSearchConfig {

    @Bean
    public HeaderSearchElementFactory getHeaderSearchElementFactory() {
        return HeaderSearchElementFactory.getInstance();
    }
}
