package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.*;

import java.beans.Visibility;
import java.util.Collection;

public interface IFChatService {
    Chat getChatById(Long id);

    Collection<Chat> getChatsByUser(User user);

    Collection<Chat> getAll();

    ChatMembership addUserToChat(User user, Chat chat);

    ChatMembership addUserToChat(User user, Chat chat, ChatMemberStatus status);

    GroupChat createGroupChat(User creator, GroupVisibility visibility);

    GroupChat createGroupChat(User creator, GroupChat chat);
}
