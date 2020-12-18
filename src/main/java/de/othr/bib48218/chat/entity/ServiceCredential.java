package de.othr.bib48218.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class ServiceCredential extends IdEntity {
    private String loginToken;
    private ServiceType serviceType;

    public ServiceCredential(String loginToken, ServiceType serviceType) {
        this.loginToken = loginToken;
        this.serviceType = serviceType;
    }
}
