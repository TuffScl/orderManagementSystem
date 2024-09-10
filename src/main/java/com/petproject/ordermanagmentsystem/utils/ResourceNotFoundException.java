package com.petproject.ordermanagmentsystem.utils;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String string){
        super(string);
    }
}
