package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.PeerChat;

import java.util.Optional;

public interface PeerChatRepository extends ChatRepository<PeerChat> {
    @Override
    Optional<PeerChat> findById(Long id);
}
