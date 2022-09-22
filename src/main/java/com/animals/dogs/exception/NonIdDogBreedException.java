package com.animals.dogs.exception;

public class NonIdDogBreedException extends Exception{

    public NonIdDogBreedException(String errorMessage) {
        super(errorMessage);
    }
}
