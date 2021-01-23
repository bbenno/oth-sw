package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.ChatMemberStatus;
import de.othr.bib48218.chat.entity.ChatMembership;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import java.util.Optional;

public interface IFChatService {

    /* GET Chat  **********************************************************************************/

    /**
     * Returns the chat with the given id.
     *
     * @param id the unique chat id
     * @return the chat if present
     */
    Optional<? extends Chat> getChatById(Long id);

    /**
     * Returns group chat with the given id.
     *
     * @param id the unique chat id
     * @return the chat if present
     */
    Optional<GroupChat> getGroupChatById(Long id);

    /**
     * Returns group chat with the given id.
     *
     * @param id the unique chat id
     * @return the chat if present
     */
    Optional<PeerChat> getPeerChatById(Long id);

    /**
     * Returns all chats the given user is member in.
     *
     * @param user the user to select the chats
     * @return all chats the given user is member in
     */
    Collection<Chat> getChatsByUser(User user);

    /* GET Chats  *********************************************************************************/

    /**
     * Returns all {@link GroupChat} and {@link PeerChat} objects that are stored.
     *
     * @return all chats
     */
    Collection<Chat> getAllChats();

    /**
     * Returns all {@link GroupChat} object that are stored.
     *
     * @return all group chats
     */
    Collection<GroupChat> getAllGroupChats();

    /**
     * Returns all {@link GroupChat} object with public visibility.
     *
     * @return all group chats
     */
    Collection<GroupChat> getAllPublicGroupChats();

    /**
     * Returns all {@link PeerChat} object that are stored.
     *
     * @return all peer chats
     */
    Collection<PeerChat> getAllPeerChats();

    /* NEW Chat  **********************************************************************************/

    /**
     * Creates new {@link GroupChat} if not yet present. The creating user will be administrator by
     * default.
     *
     * @param creator    the creating user
     * @param visibility the visibility of the created group chat
     * @return created group chat
     */
    GroupChat createGroupChat(User creator, GroupVisibility visibility);

    /**
     * Saves new {@link GroupChat} if not yet present.
     *
     * @param creator the creating user
     * @param chat    the group chat to save
     * @return saved group chat
     */
    GroupChat saveChat(User creator, GroupChat chat);

    /**
     * Saves new {@link PeerChat} if not yet present.
     *
     * This method should be avoided. Use {@link #getOrCreatePeerChatOf(User, User)} instead
     *
     * @param creator the creating user
     * @param chat    the peer chat to save
     * @return saved peer chat
     */
    PeerChat saveChat(User creator, PeerChat chat);

    /**
     * Returns {@link PeerChat} between two given users.
     *
     * @param user      the first user of the peer chat
     * @param otherUser the second user of the peer chat
     * @return peer chat with both users as members
     */
    PeerChat getOrCreatePeerChatOf(User user, User otherUser);

    /* DELETE Chat  *******************************************************************************/

    /**
     * Deletes chat with the given id.
     *
     * @param id the unique chat id
     */
    void deleteChat(Long id);

    /**
     * Delete given chat.
     *
     * @param chat the chat to delete
     */
    void deleteChat(Chat chat);

    /* UPDATE Chat  *******************************************************************************/

    /**
     * Save edited group chat.
     *
     * @param chat the group chat to save
     */
    void editGroupChat(Long id, GroupChat chat);

    // There is nothing to update of a PeerChat

    /* GET Membership  ****************************************************************************/

    /**
     * Returns {@link ChatMemberStatus} of user in chat.
     *
     * @param chat the chat to look up
     * @param user the user in the chat
     * @return membership status of user in chat
     */
    Optional<ChatMemberStatus> getChatMembership(Chat chat, User user);

    /**
     * Returns whether given user is member of chat.
     *
     * @param user the user to check
     * @param chat the chat to check for
     * @return <code>true</code> if user is member of chat; otherwise <code>false</code>
     */
    boolean isUserMember(User user, Chat chat);

    /* ADD Member  ********************************************************************************/

    /**
     * Add {@link User} to {@link Chat} as normal member.
     *
     * @param user the user to add
     * @param chat the chat to add to
     */
    void addUserToChat(User user, Chat chat);

    /**
     * Add {@link User} to {@link Chat} with given {@link ChatMemberStatus}.
     *
     * @param user   the user to add
     * @param chat   the chat to add to
     * @param status the membership status of the user in the chat
     */
    void addOrUpdateChatMembership(User user, Chat chat, ChatMemberStatus status);

    /* DELETE Member  *****************************************************************************/

    /**
     * Deletes chat membership of user in chat.
     *
     * @param user   the user to leave chat
     * @param chatId the id of the chat
     */
    void deleteChatMembership(User user, Long chatId);
}
