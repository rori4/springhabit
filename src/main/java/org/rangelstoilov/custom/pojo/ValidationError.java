package org.rangelstoilov.custom.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class ValidationError {

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;

    private final String errorMessage;

    public ValidationError(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void addValidationError(String error) {
        errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}