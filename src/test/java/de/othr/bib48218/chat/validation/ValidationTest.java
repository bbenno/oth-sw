package de.othr.bib48218.chat.validation;

import javax.validation.Validation;
import javax.validation.Validator;

public abstract class ValidationTest {

    protected final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
}
