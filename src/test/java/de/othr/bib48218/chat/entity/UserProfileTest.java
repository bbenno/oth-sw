package de.othr.bib48218.chat.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class UserProfileTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveName() {
        var chatProfile = new ChatProfile();

        assertThrows(NullPointerException.class, () -> chatProfile.setName(null));
        assertThrows(NullPointerException.class, () -> new ChatProfile(null, "description"));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<UserProfile>) UserProfile::new);
    }
}
