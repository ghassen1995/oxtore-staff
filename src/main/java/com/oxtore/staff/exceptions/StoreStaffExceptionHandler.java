package com.oxtore.staff.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StoreStaffExceptionHandler {

    @ExceptionHandler(StaffAlreadyExistsException.class)
    public ResponseEntity<String> handleStaffAlreadyExists(StaffAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(StaffNotFoundException.class)
    public ResponseEntity<String> handleStaffNotFound(StaffNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SupervisorAlreadyExistsException.class)
    public ResponseEntity<String> handleSupervisorAlreadyExists(SupervisorAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidStaffOperationException.class)
    public ResponseEntity<String> handleInvalidStaffOperation(InvalidStaffOperationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(ex.getMessage());
    }
}