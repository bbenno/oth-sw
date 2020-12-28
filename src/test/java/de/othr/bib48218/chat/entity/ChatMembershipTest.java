package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatMembershipTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveChat() {
        var chatMembership = new ChatMembership();

        assertThrows(NullPointerException.class, () -> chatMembership.setChat(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            null,
            ChatMemberStatus.MEMBER,
            new User() {
            }));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveStatus() {
        var chatMembership = new ChatMembership();

        assertThrows(NullPointerException.class, () -> chatMembership.setStatus(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            new Chat() {
            },
            null,
            new User() {
            }));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveUser() {
        var chatMembership = new ChatMembership();

        assertThrows(NullPointerException.class, () -> chatMembership.setUser(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            new Chat() {
            },
            ChatMemberStatus.MEMBER,
            null));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<ChatMembership>) ChatMembership::new);
    }
}
