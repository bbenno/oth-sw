package de.othr.bib48218.chat.rest;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration providing {@link RestTemplate} beans.
 */
@Configuration
public class RestTemplateBuilderConfiguration {

    @Bean
    public RestTemplate createRestTemplateBuilder(RestTemplateBuilder builder) {
        return builder.build();
    }

}
