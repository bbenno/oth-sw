package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Chat;
import de.othr.bib48218.chat.entity.GroupChat;
import de.othr.bib48218.chat.entity.GroupVisibility;
import de.othr.bib48218.chat.entity.PeerChat;
import de.othr.bib48218.chat.entity.User;
import java.util.Collection;
import java.util.Optional;

public interface IFChatService {

    /* GET Chat  **********************************************************************************/

    /**
     * Gets the chat with a certain id.
     *
     * @param id the number identifying a chat
     * @return the chat
     */
    Optional<Chat> getChatById(Long id);

    /**
     * Returns the group chat with a certain id.
     *
     * @param id the number identifying a chat
     * @return the chat
     */
    Optional<GroupChat> getGroupChatById(Long id);

    /**
     * Returns the group chat with a certain id.
     *
     * @param id the number identifying a  chat
     * @return the chat
     */
    Optional<PeerChat> getPeerChatById(Long id);

    /**
     * Gets all chats where a certain user is member of.
     *
     * @param user the user
     * @return the collection containing the chats
     */
    Collection<Chat> getChatsByUser(User user);

    /* GET Chats  *********************************************************************************/

    /**
     * Gets all chats, including all {@link GroupChat}s and all {@link PeerChat}s.
     *
     * @return the collection containing all chats
     */
    Collection<Chat> getAllChats();

    /**
     * Gets all group chats.
     *
     * @return the collection containing all group chats
     */
    Collection<GroupChat> getAllGroupChats();

    /**
     * Gets all public group chats.
     *
     * @return the collection containing all group chats
     */
    Collection<GroupChat> getAllPublicGroupChats();

    /**
     * Gets all peer chats.
     *
     * @return the collection containing all peer chats
     */
    Collection<PeerChat> getAllPeerChats();

    /**
     * Gets the chats that should be found by a certain search string.
     *
     * @param searchFragment the string containing a search term
     * @return the collection containing group chats
     */
    Collection<GroupChat> getChatsByStringFragment(String searchFragment);

    /* NEW Chat  **********************************************************************************/

    /**
     * Creates and saves a group chat with certain creator and visibility.
     *
     * @param creator    the user creating the group chat
     * @param visibility the group visibility of the group chat
     * @return the saved group chat
     */
    GroupChat createGroupChat(User creator, GroupVisibility visibility);

    /**
     * Saves a certain group chat.
     *
     * @param creator the user creating the group chat
     * @param chat    the group chat to save
     * @return the saved group chat
     */
    GroupChat saveChat(User creator, GroupChat chat);

    /**
     * Gets or creates the peer chat with certain users being member of.
     *
     * @param user      the user being member of the peer chat
     * @param otherUser the user being member of the peer chat
     * @return the peer chat
     */
    PeerChat getOrCreatePeerChatOf(User user, User otherUser);

    /* DELETE Chat  *******************************************************************************/

    /**
     * Deletes a certain chat.
     *
     * @param chat the chat
     */
    void deleteChat(Chat chat);
}
