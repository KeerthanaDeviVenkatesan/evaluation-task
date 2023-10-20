package com.springboot.evaluationtask.DashboardModule.exception;

public class ArgumentConstraintViolation extends Throwable {
    public ArgumentConstraintViolation(String message) {
        super(message);
    }
}
