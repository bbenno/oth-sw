package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.ChatProfile;
import de.othr.bib48218.chat.entity.GroupChat;

import java.util.Optional;

public interface ChatProfileRepository extends ProfileRepository<ChatProfile> {
    Optional<ChatProfile> findByChat(GroupChat chat);
}
