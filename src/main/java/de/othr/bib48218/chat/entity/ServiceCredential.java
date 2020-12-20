package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class ServiceCredential extends IdEntity {
    @lombok.NonNull
    @org.springframework.lang.NonNull
    private String loginToken;
    @lombok.NonNull
    @org.springframework.lang.NonNull
    private ServiceType serviceType;
}
