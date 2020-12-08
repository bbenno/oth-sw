package de.othr.bib48218.chat.service;

import java.util.Collection;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.User;

public interface IFSendMessage {
    User findUserByUsername(String username, String serviceToken);

    Boolean sendMessage(Message message, String serviceToken);

    Collection<Message> pullMessages(String serviceToken);
}
