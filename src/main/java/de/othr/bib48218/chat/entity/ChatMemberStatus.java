package de.othr.bib48218.chat.entity;

import java.util.ResourceBundle;

public enum ChatMemberStatus {
    ADMINISTRATOR,
    MEMBER,
    RESTRICTED,
    LEFT,
    KICKED,
    ;

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("lang");

    @Override
    public String toString() {
        return resourceBundle.getString("enum.chat_member_status." + this.name());
    }

    public static boolean isAllowedToEditChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }

    public static boolean isAllowedToDeleteChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }

    public static boolean isAllowedToAddMembersToChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }
}
