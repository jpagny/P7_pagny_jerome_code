package com.nnk.springboot.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(Integer id) {
        super("Resource doesn't exist with id : " + id);
    }
}