package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttachmentTest {
    private static final Attachment validAttachment = new Attachment("personal_image", "application/png");

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
