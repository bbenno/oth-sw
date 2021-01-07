package de.othr.bib48218.chat.factory;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;

public class ChatFactory {
    private static final Faker faker = new Faker();

    public static GroupChat newValidGroupChat() {
        int randIdx = faker.random().nextInt(GroupVisibility.values().length);
        GroupVisibility visibility = GroupVisibility.values()[randIdx];
        return new GroupChat(visibility);
    }

    public static GroupChat newValidGroupChat(GroupVisibility visibility) {
        return new GroupChat(visibility);
    }

    public static PeerChat newValidPeerChat() {
        return new PeerChat();
    }
}
