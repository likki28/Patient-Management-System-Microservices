package com.pm.patientservice.exception;

public class PatientDoesNotExistException extends RuntimeException {
    public PatientDoesNotExistException(String message) {
        super(message);
    }
}
