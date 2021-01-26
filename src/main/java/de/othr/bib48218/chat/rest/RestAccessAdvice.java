package de.othr.bib48218.chat.rest;

import de.othr.bib48218.chat.util.RestAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class RestAccessAdvice {

    @ResponseBody
    @ExceptionHandler(RestAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(RestAccessException ex) {
        return ex.getMessage();
    }

}
