package com.animals.dogs.service.impl;

import com.animals.dogs.exception.DogBreedExistException;
import com.animals.dogs.exception.NonIdDogBreedException;
import com.animals.dogs.model.DogBreed;
import com.animals.dogs.repository.DogBreedRepository;
import com.animals.dogs.service.DogBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogBreedServiceImpl implements DogBreedService {

    private final DogBreedRepository dogBreedRepository;

    @Autowired
    public DogBreedServiceImpl(DogBreedRepository dogBreedRepository) {
        this.dogBreedRepository = dogBreedRepository;
    }

    @Override
    public Optional<DogBreed> getDogBreedById(Long id) {
        return dogBreedRepository.findById(id);
    }

    @Override
    public Optional<DogBreed> getDogBreedByName(String breedName) {
        return dogBreedRepository.findByBreedName(breedName);
    }

    @Override
    public List<DogBreed> getDogBreedSmallerThan(Long maxHeight) {
        return dogBreedRepository.findByHeightLessThan(maxHeight + 1L);
    }

    @Override
    public DogBreed saveDogBreed(DogBreed dogBreed) throws DogBreedExistException {
        if (dogBreed.getId() == null) {
            return dogBreedRepository.save(dogBreed);
        } else {
            throw new DogBreedExistException("The dog breed id must be null");
        }
    }

    @Override
    public DogBreed updateDogBreed(DogBreed dogBreed) throws NonIdDogBreedException {
        if (dogBreed.getId() != null) {
            return dogBreedRepository.save(dogBreed);
        } else {
            throw new NonIdDogBreedException("The dog breed should have an id in order to update it");
        }
    }

    @Override
    public void deleteDogBreed(Long id) {
        dogBreedRepository.deleteById(id);
    }
}
