package com.nnk.springboot.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String resource) {
        super("Resource doesn't exist with id : " + resource);
    }

}