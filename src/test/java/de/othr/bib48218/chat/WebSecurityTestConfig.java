package de.othr.bib48218.chat;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@TestConfiguration
@Import(WebSecurityUtilities.class)
public class WebSecurityTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager();
    }
}
