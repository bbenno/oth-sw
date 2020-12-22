package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupChatTest {
    @Test
    void shouldHaveVisibility() {
        var groupChat = new GroupChat();

        assertThrows(NullPointerException.class, () -> groupChat.setVisibility(null));
        assertThrows(NullPointerException.class, () -> new GroupChat(null));
    }
}
