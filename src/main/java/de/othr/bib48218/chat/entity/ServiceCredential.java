package de.othr.bib48218.chat.entity;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.persistence.Entity;

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
