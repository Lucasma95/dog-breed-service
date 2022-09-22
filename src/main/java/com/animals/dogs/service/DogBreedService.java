package com.animals.dogs.service;

import com.animals.dogs.exception.DogBreedExistException;
import com.animals.dogs.exception.NonIdDogBreedException;
import com.animals.dogs.model.DogBreed;

import java.util.List;
import java.util.Optional;

public interface DogBreedService {

    Optional<DogBreed> getDogBreedById(Long id);

    Optional<DogBreed> getDogBreedByName(String breedName);

    List<DogBreed> getDogBreedSmallerThan(Long maxHeight);

    DogBreed saveDogBreed(DogBreed dogBreed) throws DogBreedExistException;

    DogBreed updateDogBreed(DogBreed dogBreed) throws NonIdDogBreedException;

    void deleteDogBreed(Long id);
}
