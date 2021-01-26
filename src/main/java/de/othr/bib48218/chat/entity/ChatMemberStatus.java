package de.othr.bib48218.chat.entity;

/**
 * The membership status of an {@link User} in a {@link Chat}.
 */
public enum ChatMemberStatus {
    /**
     * Allowed to modify chat.
     */
    ADMINISTRATOR,
    /**
     * Allowed to read and write messages.
     */
    MEMBER,
    /**
     * Allowed to read messages.
     */
    RESTRICTED,
    LEFT,
    KICKED,
    ;

    /**
     * Returns whether {@link User} with certain status is allowed to edit chat.
     *
     * @param status the membership status of a user
     * @return <code>true</code> if user is allowed to edit; otherwise <code>false</code>
     */
    public static boolean isAllowedToEditChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }

    /**
     * Returns whether {@link User} with certain status is allowed to delete chat.
     *
     * @param status the membership status of a user
     * @return <code>true</code> if user is allowed to delete; otherwise <code>false</code>
     */
    public static boolean isAllowedToDeleteChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }

    /**
     * Returns whether {@link User} with certain status is allowed to add further members to chat.
     *
     * @param status the membership status of a user
     * @return <code>true</code> if user is allowed to add members; otherwise <code>false</code>
     */
    public static boolean isAllowedToAddMembersToChat(ChatMemberStatus status) {
        return status == ADMINISTRATOR;
    }
}
