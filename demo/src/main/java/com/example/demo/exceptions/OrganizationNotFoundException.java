package com.example.demo.exceptions;

public class OrganizationNotFoundException extends ResourceNotFoundException{
    public OrganizationNotFoundException(String message) {
        super(message);
    }
}
