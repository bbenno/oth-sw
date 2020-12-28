package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.WebSecurityTestConfig;
import de.othr.bib48218.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import(WebSecurityTestConfig.class)
public class UserControllerTest {
    private static final String namespace_prefix = "/user";
    private static final String post_create_url = namespace_prefix + "/new";
    private static final String form_url = namespace_prefix + "/new";
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;
}
