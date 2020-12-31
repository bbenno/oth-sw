package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.User;

import java.util.Collection;

public interface IFChatService {
    Chat getChatById(Long id);

    Collection<Chat> getChatsByUser(User user);

    Collection<Chat> getAll();
}
