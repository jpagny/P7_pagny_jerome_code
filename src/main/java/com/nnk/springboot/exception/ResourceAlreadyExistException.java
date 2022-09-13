package com.nnk.springboot.exception;

public class ResourceAlreadyExistException extends Exception {

    public ResourceAlreadyExistException(String resource) {
        super("Resource is already exist with resource : " + resource);
    }

}
