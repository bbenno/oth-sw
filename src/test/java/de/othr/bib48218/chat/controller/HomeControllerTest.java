package de.othr.bib48218.chat.controller;

import de.othr.bib48218.chat.HeaderSearchElementFactory;
import de.othr.bib48218.chat.WebSecurityTestConfig;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.UserFactory;
import de.othr.bib48218.chat.service.IFChatService;
import de.othr.bib48218.chat.service.IFUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@Import(WebSecurityTestConfig.class)
public class HomeControllerTest {
    @Spy
    HeaderSearchElementFactory headerSearchElementFactory = HeaderSearchElementFactory.getInstance();
    @Autowired
    private MockMvc mvc;
    @MockBean
    private IFUserService userService;
    @MockBean
    private IFChatService chatService;
    private User registeredUser;

    @BeforeEach
    void createTestUser() throws Exception {
        Person person = UserFactory.newValidPerson();
        userService.createPerson(person);
        registeredUser = person;
    }

    @Test
    void homeShouldRequireAuthentication() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedToLogin());
    }

    @Test
    @WithMockUser
    void homeShouldBeAccessibleWithAuthentication() throws Exception {
        mvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    @Test
    void loginShouldFailWithoutCsrf() throws Exception {
        mvc.perform(postValidLogin())
            .andExpect(status().isForbidden());
    }

    @Test
    void loginShouldFailOnInvalidCsrf() throws Exception {
        mvc.perform(postValidLogin().with(csrf().useInvalidToken()))
            .andExpect(status().isForbidden());
    }

    @Test
    void loginShouldSucceedWithValidCsrf() throws Exception {
        mvc.perform(postValidLogin().with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void meShouldRedirectToUserView() throws Exception {
        mvc.perform(get("/me"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("/user/*"));
    }

    @Test
    @WithMockUser("username")
    void viewOfMeRedirectShouldTargetUserPage() throws Exception {
        String target = mvc.perform(get("/me")).andReturn().getResponse().getRedirectedUrl();

        assertThat(target).isNotBlank();
        assertThat(target.contains("/user/username"));
    }

    @Test
    void meShouldRequireAuthentication() throws Exception {
        mvc.perform(get("/me"))
            .andExpect(redirectedToLogin());
    }

    private MockHttpServletRequestBuilder postValidLogin() {
        return post("/login")
            .param("username", registeredUser.getUsername())
            .param("password", registeredUser.getPassword());
    }

    private ResultMatcher redirectedToLogin() {
        return redirectedUrlPattern("**/login");
    }
}
