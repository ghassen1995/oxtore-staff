package com.oxtore.staff.exceptions;

public class StaffAlreadyExistsException extends RuntimeException {
    public StaffAlreadyExistsException(String message) { super(message); }
}