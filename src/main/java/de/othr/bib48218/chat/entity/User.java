package de.othr.bib48218.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * The account class of the application.
 */
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = Person.class, name = "person"),
    @Type(value = Bot.class, name = "bot")
})
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class User implements UserDetails {

    /**
     * The string identifying user.
     */
    @Id
    @NonNull
    @lombok.NonNull
    @NotBlank
    @NotNull(message = "has to be present")
    @Size(min = 1, max = 20)
    private String username;

    /**
     * The credential for login.
     */
    @JsonIgnore
    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    @Size(min = 8, max = 80)
    private String password;

    /**
     * The profile.
     */
    @OneToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true)
    private UserProfile profile = new UserProfile("");

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER,
        orphanRemoval = true)
    private Set<UserPermission> userPermissions = Collections.emptySet();

    /**
     * The chat memberships.
     */
    @JsonIgnore
    @OneToMany(
        mappedBy = "user",
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true)
    private Set<ChatMembership> memberships = Collections.emptySet();

    /**
     * The sent messages in all chats.
     */
    @JsonIgnore
    @OneToMany(
        mappedBy = "author",
        cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private Collection<Message> messages = Collections.emptySet();

    /**
     * Is account enabled to login.
     */
    @NonNull
    private boolean enabled = true;

    /**
     * Is the password not expired.
     */
    @NonNull
    private boolean credentialsNonExpired = true;

    /**
     * Is the account immutable.
     */
    @NonNull
    private boolean accountNonLocked = true;

    /**
     * Is the account expired.
     */
    @NonNull
    private boolean accountNonExpired = true;

    /**
     * The application scope.
     * <p>
     * The application separates user base into disjunctive sets, one for each {@link ServiceType}.
     */
    @NonNull
    @lombok.NonNull
    private ServiceType scope = ServiceType.CHAT;

    /**
     * Gets the authority.
     *
     * @return the authority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserPermission permission : userPermissions) {
            authorities.add(new Authority(permission.getPermission().getName()));
        }
        return authorities;
    }

    /**
     * Gets special name.
     *
     * @return the string containing the special name
     */
    protected String asString() {
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return equals((User) o);
    }

    public boolean equals(User other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        return username.equals(other.username);
    }

    @Override
    public String toString() {
        String s = profile.getName();
        if (s.isBlank()) {
            s += asString();
        }
        if (s.isBlank()) {
            s += username;
        }
        if (!enabled) {
            s += " (disabled)";
        }
        return s;
    }
}
