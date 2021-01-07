package de.othr.bib48218.chat.entity;

import de.othr.bib48218.chat.Authority;
import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UserProfile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserPermission> userPermissions = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Collection<ChatMembership> memberships;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (UserPermission permission : userPermissions) {
            authorities.add(new Authority(permission.getPermission().getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return username;
    }
}
