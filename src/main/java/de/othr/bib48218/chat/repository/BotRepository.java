package de.othr.bib48218.chat.repository;

import de.othr.bib48218.chat.entity.Bot;

import java.util.Optional;

public interface BotRepository extends UserRepository<Bot> {
    @Override
    Optional<Bot> findById(String id);
}
