package de.othr.bib48218.chat.entity;

import javax.persistence.Entity;

@Entity
public class ServiceCredential extends IdEntity {
    private String loginToken;
    private ServiceType serviceType;
}
