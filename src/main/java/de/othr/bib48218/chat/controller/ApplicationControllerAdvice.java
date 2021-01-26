package de.othr.bib48218.chat.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ApplicationControllerAdvice {

    @Value("${spring.application.name}")
    private String applicationName;

    @ModelAttribute("applicationName")
    public String getApplicationName() {
        return applicationName;
    }
    
}