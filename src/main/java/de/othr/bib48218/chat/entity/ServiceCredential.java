package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class ServiceCredential extends IdEntity {
    private String loginToken;
    private ServiceType serviceType;

    protected ServiceCredential() {
    }

    public ServiceCredential(String loginToken, ServiceType serviceType) {
        this.loginToken = loginToken;
        this.serviceType = serviceType;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public ServiceType getServiceType() {
        return this.serviceType;
    }
}
