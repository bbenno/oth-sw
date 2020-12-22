package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttachmentTest {
    @Test
    void nameShouldNotBeNull() {
        var attachment = new Attachment();

        assertThrows(Exception.class, () -> attachment.setName(null));
    }

    @Test
    void mimeTypeShouldNotBeNull() {
        var attachment = new Attachment();

        assertThrows(Exception.class, () -> attachment.setMimeType(null));
    }
}
