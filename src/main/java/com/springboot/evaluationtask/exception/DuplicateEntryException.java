package com.springboot.evaluationtask.exception;

public class DuplicateEntryException  extends RuntimeException{
    public DuplicateEntryException(String message) {
        super(message);
    }
}
