package de.othr.bib48218.chat.factory;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;

public class ChatFactory {
    private static Faker faker = new Faker();

    public static GroupChat newValidGroupChat() {
        return new GroupChat(GroupVisibility.PUBLIC);
    }

    public static PeerChat newValidPeerChat() {
        return new PeerChat();
    }
}
