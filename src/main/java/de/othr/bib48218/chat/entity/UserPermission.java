package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Assigment of a certain {@link Permission} to a certain {@link User}.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPermission extends IdEntity {

    /**
     * The user.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    /**
     * The permission.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Permission permission;

    /**
     * Class constructor.
     *
     * @param user the user
     * @param permission the
     */
    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }
}
