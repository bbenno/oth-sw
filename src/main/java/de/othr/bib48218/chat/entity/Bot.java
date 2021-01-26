package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Bot extends User {

    private ServiceType serviceType;

    public Bot(@lombok.NonNull @NonNull String username) {
        super(username, "");
    }

    public Bot(@lombok.NonNull @NonNull String username, ServiceType serviceType) {
        super(username, "");
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return super.toString() + " (Bot)";
    }
}
