package de.othr.bib48218.chat.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServiceCredentialTest {
    @Test
    void shouldHaveType() {
        var serviceCredential = new ServiceCredential();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> serviceCredential.setServiceType(null));
    }

    @Test
    void shouldHaveToken() {
        var serviceCredential = new ServiceCredential();

        // noinspection ConstantConditions
        assertThrows(NullPointerException.class, () -> serviceCredential.setLoginToken(null));
    }
}
