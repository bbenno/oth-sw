package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

/**
 * A account with certain messaging rules.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bot extends User {

    /**
     * Class constructor specifying the username.
     *
     * @param username the account name.
     */
    public Bot(@lombok.NonNull @NonNull String username) {
        super(username, "");
    }

    /**
     * Class constructor specifying the username and service type.
     *
     * @param username    the account name.
     * @param serviceType the service type
     */
    public Bot(@lombok.NonNull @NonNull String username, ServiceType serviceType) {
        super(username, "");
        this.setScope(serviceType);
    }

    @Override
    public String toString() {
        return super.toString() + " (Bot)";
    }
}
