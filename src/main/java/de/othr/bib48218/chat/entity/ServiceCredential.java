package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCredential extends IdEntity {

    @NonNull
    private String loginToken;
    @NonNull
    private ServiceType serviceType;
}
