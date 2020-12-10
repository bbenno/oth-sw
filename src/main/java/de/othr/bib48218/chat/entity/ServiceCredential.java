package de.othr.bib48218.chat.entity;

import lombok.Getter;
import javax.persistence.Entity;

@Entity
@Getter
public class ServiceCredential extends IdEntity {
    private String loginToken;
    private ServiceType serviceType;

    protected ServiceCredential() {
    }

    public ServiceCredential(String loginToken, ServiceType serviceType) {
        this.loginToken = loginToken;
        this.serviceType = serviceType;
    }
}
