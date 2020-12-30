package de.othr.bib48218.chat.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class ServiceTestContextConfiguration {
    @Bean
    IFUserService userService() {
        return new UserService();
    }

    @Bean
    IFChatService chatService() {
        return new ChatService();
    }
}
