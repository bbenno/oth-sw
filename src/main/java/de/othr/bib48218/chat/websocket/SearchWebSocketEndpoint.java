package de.othr.bib48218.chat.websocket;

import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import java.security.Principal;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Web socket endpoint controller for search bar.
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Controller
@Scope(scopeName = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
class SearchWebSocketEndpoint {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private IFUserService userService;

    @Autowired
    private IFChatService chatService;

    @MessageMapping("/search")
    @SendTo("/websocket-broker/search")
    public Collection<Pair<String, String>> receiveFromWebSocket(String searchTerm,
        Principal principal
    ) {
        System.out.println(searchTerm);
        return Stream.concat(
            userService.getUsersByStringFragment(searchTerm).stream()
                .filter(u -> !u.getUsername().equals(principal.getName()))
                .map(u -> Pair.of(u.getUsername(), u.toString() + " (" + u.getUsername() + ")")),
            chatService.getChatsByStringFragment(searchTerm).stream()
                .map(c -> Pair.of(c.getId().toString() + "/join", c.getProfile().getName()))
        ).collect(Collectors.toUnmodifiableList());
    }
}
