package com.devsuperior.clientsapi.services.exceptions;

public class DatabaseException extends RuntimeException{

    private static final long seriaLVersionUID = 1L;
    public DatabaseException(String msg) {
        super(msg);
    }
}