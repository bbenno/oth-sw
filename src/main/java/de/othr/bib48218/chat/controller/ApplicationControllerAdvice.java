package de.othr.bib48218.chat.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Advice for controller.
 */
@ControllerAdvice
public class ApplicationControllerAdvice {

    /**
     * The name of this application.
     */
    @Value("${spring.application.name}")
    private String applicationName;

    /**
     * Gets the name of this application.
     *
     * @return the name of this application
     */
    @ModelAttribute("applicationName")
    public String getApplicationName() {
        return applicationName;
    }

}
