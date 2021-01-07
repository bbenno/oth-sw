package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PeerChatTest {
    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow(PeerChat::new);
    }
}
