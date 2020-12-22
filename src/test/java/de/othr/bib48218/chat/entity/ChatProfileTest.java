package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatProfileTest {
    @Test
    void shouldHaveDescription() {
        var chatProfile = new ChatProfile();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> chatProfile.setDescription(null));
    }

    @Test
    void shouldHaveName() {
        var chatProfile = new ChatProfile();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> chatProfile.setName(null));
    }
}