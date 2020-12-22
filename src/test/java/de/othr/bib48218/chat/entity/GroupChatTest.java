package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupChatTest {
    @Test
    void shouldHaveVisibility() {
        var groupChat = new GroupChat();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> groupChat.setVisibility(null));
    }
}
