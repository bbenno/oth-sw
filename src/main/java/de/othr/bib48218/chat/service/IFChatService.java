package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.*;

import java.util.Collection;
import java.util.Optional;

public interface IFChatService {
    Optional<? extends Chat> getChatById(Long id);

    Collection<Chat> getChatsByUser(User user);

    Collection<Chat> getAll();

    ChatMembership addUserToChat(User user, Chat chat);

    ChatMembership addUserToChat(User user, Chat chat, ChatMemberStatus status);

    GroupChat createGroupChat(User creator, GroupVisibility visibility);

    GroupChat createGroupChat(User creator, GroupChat chat);

    PeerChat getPeerChatOf(User user, User otherUser);
}
