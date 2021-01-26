package de.othr.bib48218.chat.util;

import de.othr.bib48218.chat.entity.ServiceType;

public class RestAccessException extends RuntimeException {

    public RestAccessException() {
        super("Missing or wrong access token");
    }

    public RestAccessException(ServiceType serviceType) {
        super("Missing or wrong access token for service " + serviceType);
    }

}
