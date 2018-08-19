package org.rangelstoilov.custom.exceptions;

public class EmailExistsException extends Exception {
    public EmailExistsException(String message) {
        super(message);
    }
}
