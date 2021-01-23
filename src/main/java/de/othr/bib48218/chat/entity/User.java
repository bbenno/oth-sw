package de.othr.bib48218.chat.entity;

import de.othr.bib48218.chat.Authority;
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

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode
public abstract class User implements UserDetails, HeaderSearchElement {

    @Id
    @NonNull
    @lombok.NonNull
    @NotBlank
    @NotNull(message = "has to be present")
    @Size(min = 2, max = 20)
    private String username;

    @NonNull
    @lombok.NonNull
    @NotNull
    @NotBlank
    @Size(min = 8, max = 80)
    private String password;

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

    @OneToMany(
        mappedBy = "user",
        cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE},
        orphanRemoval = true)
    private Set<ChatMembership> memberships = Collections.emptySet();

    @OneToMany(
        mappedBy = "author",
        cascade = {CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private Collection<Message> messages = Collections.emptySet();

    @NonNull
    private boolean enabled = true;

    @NonNull
    private boolean credentialsNonExpired = true;

    @NonNull
    private boolean accountNonLocked = true;

    @NonNull
    private boolean accountNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserPermission permission : userPermissions) {
            authorities.add(new Authority(permission.getPermission().getName()));
        }
        return authorities;
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
        if (profile == null || profile.getName().isBlank()) {
            return username;
        } else {
            return profile.getName();
        }
    }
}
