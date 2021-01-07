package de.othr.bib48218.chat.controller;

import com.github.javafaker.Faker;
import de.othr.bib48218.chat.HeaderSearchElementFactory;
import de.othr.bib48218.chat.WebSecurityTestConfig;
import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ChatController.class)
@Import(WebSecurityTestConfig.class)
public class ChatControllerTest {
    @Spy
    HeaderSearchElementFactory headerSearchElementFactory = HeaderSearchElementFactory.getInstance();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IFUserService userService;
    @MockBean
    private IFChatService chatService;

    private static String anyUsername() {
        return Faker.instance().name().username();
    }

    private static Chat anyChat() {
        return ChatFactory.newValidGroupChat();
    }

    // ToDo: chat.id must not be null
    //@Test
    @WithMockUser
    void shouldRedirectToChatAfterAddingUser() throws Exception {
        Long id = 1L;
        Chat chat = anyChat();
        //when(chatService.getChatById(id)).thenReturn(Optional.of(chat));
        mvc.perform(post("/chat/" + id + "/add").param("username", anyUsername()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/chat/" + id));
    }

    // ToDo: chat.id must not be null
    //@Test
    @WithMockUser
    void shouldDisplayAddMemberView() throws Exception {
        Long id = 1L;
        Chat chat = anyChat();
        // when(chatService.getChatById(id)).thenReturn(Optional.of(chat));
        mvc.perform(get("/chat/" + id + "/add"))
            .andExpect(status().isOk());
    }
}
