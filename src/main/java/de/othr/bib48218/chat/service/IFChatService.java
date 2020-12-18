package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;

import java.util.Optional;

public interface IFChatService {
    Optional<PeerChat> getChatBetweenUsers(User user1, User user2);
}
