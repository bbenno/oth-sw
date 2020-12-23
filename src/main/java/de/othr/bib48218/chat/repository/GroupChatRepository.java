package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.GroupChat;

import java.util.Optional;

public interface GroupChatRepository extends ChatRepository<GroupChat> {
    @Override
    Optional<GroupChat> findById(Long id);
}
