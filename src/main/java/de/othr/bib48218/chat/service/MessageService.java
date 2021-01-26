package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.integration.PartnerServiceEvent;
import de.othr.bib48218.chat.integration.PartnerServiceEventSource;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.Message;
import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.repository.MessageRepository;
import de.othr.bib48218.chat.repository.PersonRepository;
import java.time.LocalDateTime;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService implements IFMessageService {

    @Value("${partner.service.payment.bot_name}")
    private String paymentBotName;

    @Autowired
    private PartnerServiceEventSource partnerServiceEventSource;

    @Autowired
    private MessageRepository repository;

    @Autowired
    private PersonRepository userRepository;

    private boolean isServiceBotReceiving(Message message) {
        boolean isServiceBotAuthor = message.getAuthor().getUsername().equals(paymentBotName);
        boolean isPartnerServiceReceiving = message.getChat().getMemberships().stream()
            .anyMatch(m -> m.getUser().getUsername().equals(paymentBotName));
        return !isServiceBotAuthor && isPartnerServiceReceiving;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> getAllMessagesByChat(Chat chat) {
        return repository.findByChat(chat);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> getMessagesByChatSince(Chat chat, LocalDateTime time) {
        return repository.findByChatAndTimestampBefore(chat, time);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> getAllMessagesByChatFrom(Chat chat, String username) {
        return repository.findByChatAndAuthor_Username(chat, username);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Message> getMessagesByChatSinceFrom(
        Chat chat,
        LocalDateTime localDateTime,
        String username
    ) {
        return repository
            .findByChatAndTimestampBeforeAndAuthor_Username(chat, localDateTime, username);
    }

    @Override
    @Transactional
    public Message saveMessage(Message message) {
        message = repository.save(message);

        if (isServiceBotReceiving(message)) {
            partnerServiceEventSource
                .triggerEvent(new PartnerServiceEvent(message, ServiceType.PAYMENT));
        }
        return message;
    }

    @Override
    @Transactional
    public void deleteMessageById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteMessage(Message message) {
        repository.delete(message);
    }

}
