package de.othr.bib48218.chat.factory;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.User;

import java.util.Random;

public class ChatMembershipFactory {
    private static final Random random = new Random();

    public static ChatMembership newChatMembership() {
        return new ChatMembership(anyChat(), randomStatus(), anyUser());
    }

    public static ChatMembership newChatMembershipWithChat(Chat chat) {
        return new ChatMembership(chat, randomStatus(), anyUser());
    }

    public static ChatMembership newChatMembershipWithUser(User user) {
        return new ChatMembership(anyChat(), randomStatus(), user);
    }

    public static ChatMembership newChatMembershipWithUserAndChatAndStatus(User user, Chat chat, ChatMemberStatus status) {
        return new ChatMembership(chat, status, user);
    }

    private static ChatMemberStatus randomStatus() {
        return ChatMemberStatus.values()[random.nextInt(ChatMemberStatus.values().length)];
    }

    private static User anyUser() {
        return UserFactory.newValidPerson();
    }

    private static Chat anyChat() {
        return ChatFactory.newValidGroupChat();
    }
}
