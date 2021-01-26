package de.othr.bib48218.chat.service;

import de.othr.bib48218.chat.entity.Bot;
import de.othr.bib48218.chat.entity.Person;
import de.othr.bib48218.chat.entity.ServiceType;
import de.othr.bib48218.chat.entity.User;
import de.othr.bib48218.chat.util.UserAlreadyExistsException;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

/**
 * Decorator class of {@link UserService} to filter by certain {@link ServiceType}.
 */
abstract class PartnerUserService implements IFUserService {

    protected final ServiceType serviceType;

    private final UserService userService;

    protected PartnerUserService(ServiceType serviceType, UserService userService) {
        this.serviceType = serviceType;
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByFirstName(String firstName) {
        return filterByPartner(userService.getPersonByFirstName(firstName));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getPersonByLastName(String lastName) {
        return filterByPartner(userService.getPersonByLastName(lastName));
    }

    @Override
    public Collection<Person> getPersonByEmail(String email) {
        return filterByPartner(userService.getPersonByEmail(email));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUsername(String username) {
        return filterByPartner(userService.getUserByUsername(username));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getUsersByStringFragment(String usernamePattern) {
        return filterByPartner(userService.getUsersByStringFragment(usernamePattern));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Person> getPersonByUsername(String username) {
        return filterByPartner(userService.getPersonByUsername(username));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bot> getBotByUsername(String username) {
        return filterByPartner(userService.getBotByUsername(username));
    }

    @Override
    @Transactional
    public Person createPerson(Person person) throws UserAlreadyExistsException {
        return userService.createPerson(person);
    }

    @Override
    @Transactional
    public Bot createBot(Bot bot) throws UserAlreadyExistsException {
        return userService.createBot(bot);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<User> getAllUsers() {
        return filterByPartner(userService.getAllUsers());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Person> getAllPersons() {
        return filterByPartner(userService.getAllPersons());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Bot> getAllBots() {
        return filterByPartner(userService.getAllBots());
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userService.deleteUserByUsername(username);
    }

    private <TUser extends User> boolean isRightScope(TUser u) {
        return u.getScope() == serviceType;
    }

    private <TUser extends User> Collection<TUser> filterByPartner(Collection<TUser> users) {
        return users.stream().filter(this::isRightScope).collect(Collectors.toUnmodifiableList());
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private <TUser extends User> Optional<TUser> filterByPartner(Optional<TUser> user) {
        return user.filter(this::isRightScope);
    }
}
