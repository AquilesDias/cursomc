package com.aquilesd.coursemc.services.exceptions;

public class AuthorizationException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public AuthorizationException(String msg){
        super(msg);
    }

    public AuthorizationException(String msg, Throwable cause){
        super(msg, cause);
    }
}
