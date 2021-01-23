package de.othr.bib48218.chat.entity;

import java.util.ResourceBundle;

public enum GroupVisibility {
    PRIVATE,
    PUBLIC,
    ;

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");

    @Override
    public String toString() {
        return resourceBundle.getString("enum.group_chat_visibility." + this.name());
    }
}
