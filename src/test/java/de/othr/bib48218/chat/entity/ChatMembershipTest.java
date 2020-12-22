package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatMembershipTest {
    private static final ChatMembership validChatMembership = new ChatMembership();

    @Test
    void shouldHaveChat() {
        var chatMembership = new ChatMembership();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> chatMembership.setChat(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            null,
            ChatMemberStatus.MEMBER,
            new User() {
            }));
    }

    @Test
    void shouldHaveStatus() {
        var chatMembership = new ChatMembership();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> chatMembership.setStatus(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            new Chat() {
            },
            null,
            new User() {
            }));
    }

    @Test
    void shouldHaveUser() {
        var chatMembership = new ChatMembership();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> chatMembership.setUser(null));
        assertThrows(NullPointerException.class, () -> new ChatMembership(
            new Chat() {
            },
            ChatMemberStatus.MEMBER,
            null));
    }
}
