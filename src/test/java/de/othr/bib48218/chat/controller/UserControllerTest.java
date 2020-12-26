package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    private static final String namespace_prefix = "/user";
    private static final String post_create_url = namespace_prefix + "/new";
    private static final String form_url = namespace_prefix + "/new";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void personFormShowTest() throws Exception {
        mvc.perform(get(form_url))
            .andExpect(model().attribute("person", any(Person.class)))
            .andExpect(view().name("user/new_person"))
            .andExpect(status().isOk());
    }

    @Test
    void personFormPostTest() throws Exception {
        final String username = "test_user";
        mvc.perform(post(post_create_url).param("username", username).param("password", "abc"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl(username));
    }
}
