package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.WebSecurityTestConfig;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegisterController.class)
@Import(WebSecurityTestConfig.class)
public class RegisterControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldBePubliclyAccessible() throws Exception {
        mvc.perform(get("/register"))
            .andExpect(status().isOk())
            .andExpect(view().name("register"));
    }

    @Test
    void shouldAcceptRegistration() throws Exception {
        var person = UserFactory.newValidPerson();
        when(userService.createPerson(person)).thenReturn(person);
        // ToDo: extract param names from /register view.
        var params = new LinkedMultiValueMap<String, String>();
        params.add("username", person.getUsername());
        params.add("password", person.getPassword());
        params.add("firstName", person.getFirstName());
        params.add("lastName", person.getLastName());
        params.add("email", person.getEmail());

        // ToDo: extract action url from /register view.
        mvc.perform(post("/register").params(params).with(csrf()))
            .andExpect(status().is3xxRedirection());
    }
}
