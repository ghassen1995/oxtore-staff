package com.oxtore.staff.exceptions;

public class SupervisorAlreadyExistsException extends RuntimeException {
    public SupervisorAlreadyExistsException(String message) { super(message); }
}