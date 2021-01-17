package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AttachmentTest {
    @SuppressWarnings("ConstantConditions")
    @Test
    void nameShouldNotBeNull() {
        var attachment = new Attachment();

        assertThrows(NullPointerException.class, () -> attachment.setName(null));
        assertThrows(NullPointerException.class, () -> new Attachment(null, "application/image", "path"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void mimeTypeShouldNotBeNull() {
        var attachment = new Attachment();

        assertThrows(NullPointerException.class, () -> attachment.setMimeType(null));
        assertThrows(NullPointerException.class, () -> new Attachment("image", null, "path"));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void pathShouldNotBeNull() {
        var attachment = new Attachment();

        assertThrows(NullPointerException.class, () -> attachment.setPath(null));
        assertThrows(NullPointerException.class, () -> new Attachment("image", "application/image", null));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<Attachment>) Attachment::new);
    }
}
