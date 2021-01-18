package de.othr.bib48218.chat.entity;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class ServiceCredentialTest {

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveType() {
        var serviceCredential = new ServiceCredential();

        assertThrows(NullPointerException.class, () -> serviceCredential.setServiceType(null));
        assertThrows(NullPointerException.class, () -> new ServiceCredential("token", null));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    void shouldHaveToken() {
        var serviceCredential = new ServiceCredential();

        assertThrows(NullPointerException.class, () -> serviceCredential.setLoginToken(null));
        assertThrows(NullPointerException.class,
            () -> new ServiceCredential(null, ServiceType.BANK));
    }

    @Test
    void shouldHaveNoArgsConstructor() {
        assertDoesNotThrow((ThrowingSupplier<ServiceCredential>) ServiceCredential::new);
    }
}
