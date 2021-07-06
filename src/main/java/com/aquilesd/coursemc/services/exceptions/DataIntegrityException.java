package com.aquilesd.coursemc.services.exceptions;

public class DataIntegrityException extends RuntimeException{

    private static final Long serialVersionUID = 1L;

    public DataIntegrityException(String msg){
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause){
        super(msg, cause);
    }
}
