package de.othr.bib48218.chat.entity;

import java.util.ResourceBundle;

public enum ServiceType {
    WEBSHOP,
    BANK,
    PAYMENT,
    ;

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");

    @Override
    public String toString() {
        return resourceBundle.getString("enum.service_type." + this.name());
    }
}
