package com.animals.dogs.exception;

public class DogBreedExistException extends Exception {

    public DogBreedExistException(String errorMessage) {
        super(errorMessage);
    }
}
