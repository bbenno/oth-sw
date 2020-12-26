package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserPermission extends IdEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Permission permission;

    public UserPermission(User user, Permission permission) {
        this.user = user;
        this.permission = permission;
    }
}
