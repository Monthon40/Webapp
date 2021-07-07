package io.muzoo.ooc.webapp.basic.service;

public class UsernameNotUniqueException extends  UserServiceException{
    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
