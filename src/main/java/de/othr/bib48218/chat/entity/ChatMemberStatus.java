package de.othr.bib48218.chat.entity;

public enum ChatMemberStatus {
    ADMINISTRATOR,
    MEMBER,
    RESTRICTED,
    LEFT,
    KICKED,
    ;

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
