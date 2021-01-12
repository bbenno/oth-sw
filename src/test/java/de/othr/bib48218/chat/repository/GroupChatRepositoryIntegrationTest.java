package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.factory.ChatFactory;
import de.othr.bib48218.chat.factory.UserFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("testing")
public class GroupChatRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Test
    void shouldFindGroupChatByMembershipsUser() {
        User user = UserFactory.newValidPerson();
        GroupChat chat = ChatFactory.newValidGroupChat();
        ChatMembership membership = new ChatMembership(chat, ChatMemberStatus.MEMBER, user);
        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(chat);
        entityManager.persistAndFlush(membership);

        Collection<GroupChat> chats = groupChatRepository.findByMembershipsUser(user);

        assertThat(chats).isNotNull();
        assertThat(chats).isNotEmpty();
    }
}
