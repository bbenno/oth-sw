package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GroupChatTest {
    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveVisibility() {
        var groupChat = new GroupChat();

        assertThrows(NullPointerException.class, () -> groupChat.setVisibility(null));
        assertThrows(NullPointerException.class, () -> new GroupChat(null));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<GroupChat>) GroupChat::new);
    }
}
