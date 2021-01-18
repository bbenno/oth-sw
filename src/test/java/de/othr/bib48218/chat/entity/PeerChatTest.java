package de.othr.bib48218.chat.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

public class PeerChatTest {

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow(PeerChat::new);
    }
}
