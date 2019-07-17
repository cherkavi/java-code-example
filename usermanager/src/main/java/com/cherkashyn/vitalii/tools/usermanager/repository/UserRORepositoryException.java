package com.cherkashyn.vitalii.tools.usermanager.repository;

public class UserRORepositoryException extends RuntimeException {
    public UserRORepositoryException(String message) {
        super(message);
    }
    public UserRORepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
