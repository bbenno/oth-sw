package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import java.util.Collection;

public interface GroupChatRepository extends ChatRepository<GroupChat> {

    Collection<GroupChat> findByVisibilityIs(GroupVisibility visibility);

    Collection<GroupChat> findByProfileNameContains(String profileNameFragment);
}
