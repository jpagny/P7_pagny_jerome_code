package com.nnk.springboot.controller;

import com.nnk.springboot.exception.ResourceAlreadyExistException;
import com.nnk.springboot.exception.ResourceNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyErrorController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public String resourceNotFoundException(ResourceNotFoundException ex) {
        return "error/400.html";
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public String resourceAlreadyExistException(ResourceAlreadyExistException ex) {
        return "error/409.html";
    }


}
