package de.othr.bib48218.chat;

import java.security.SecureRandom;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
class WebSecurityUtilities {

    private static final int STRENGTH = 15;

    @Value("${user-password-salt}")
    private String salt;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(STRENGTH, new SecureRandom(salt.getBytes()));
    }
}
