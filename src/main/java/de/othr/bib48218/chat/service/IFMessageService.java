package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Message;

public interface IFMessageService extends IFSendMessage {
    Message saveMessage(Message message);
}
