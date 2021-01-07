package de.othr.bib48218.chat;

import de.othr.bib48218.chat.entity.HeaderSearchElement;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.stream.Collectors;

public class HeaderSearchElementFactory {
    private static HeaderSearchElementFactory instance;
    @Autowired
    private IFUserService userService;
    @Autowired
    private IFChatService chatService;

    private HeaderSearchElementFactory() {
    }

    public static HeaderSearchElementFactory getInstance() {
        if (instance == null)
            instance = new HeaderSearchElementFactory();

        return instance;
    }

    public Collection<HeaderSearchElement> getAllHeaderSearchElements() {
        Collection<HeaderSearchElement> headerSearchElements = userService.getAllUsers().stream().map((user) -> user).collect(Collectors.toList());
        headerSearchElements.addAll(chatService.getAllChats());
        return headerSearchElements;
    }
}
