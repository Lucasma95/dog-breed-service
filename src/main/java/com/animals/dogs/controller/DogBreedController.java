package com.animals.dogs.controller;

import com.animals.dogs.exception.DogBreedExistException;
import com.animals.dogs.exception.NonIdDogBreedException;
import com.animals.dogs.model.DogBreed;
import com.animals.dogs.service.DogBreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/dogBreeds")
public class DogBreedController {

    private final DogBreedService dogBreedService;

    @Autowired
    public DogBreedController(DogBreedService dogBreedService) {
        this.dogBreedService = dogBreedService;
    }

    @GetMapping("/id/{dogBreedId}")
    public ResponseEntity<DogBreed> getUserById (@PathVariable("dogBreedId") Long dogBreedId) {
        Optional<DogBreed> dog = dogBreedService.getDogBreedById(dogBreedId);
        return dog.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/breedName/{breedName}")
    public ResponseEntity<DogBreed> getUserById (@PathVariable("breedName") String breedName) {
        Optional<DogBreed> dog = dogBreedService.getDogBreedByName(breedName);
        return dog.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @GetMapping("/maxHeight/{maxHeight}")
    public ResponseEntity<List<DogBreed>> GetUserListByCountry (@PathVariable("maxHeight") Long maxHeight) {
        List<DogBreed> dogBreeds = dogBreedService.getDogBreedSmallerThan(maxHeight);
        return new ResponseEntity<>(dogBreeds, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<DogBreed> saveDogBreed (@RequestBody DogBreed dogBreed) {
        DogBreed dogBreedSaved;
        try {
            dogBreedSaved = dogBreedService.saveDogBreed(dogBreed);
        } catch (DogBreedExistException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(dogBreedSaved, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DogBreed> updateDog (@RequestBody DogBreed dogBreed) {
        DogBreed dogBreedUpdated;
        try {
            dogBreedUpdated = dogBreedService.updateDogBreed(dogBreed);
        } catch (NonIdDogBreedException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(dogBreedUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDog(@PathVariable("dogBreedId") Long dogBreedId) {
        dogBreedService.deleteDogBreed(dogBreedId);
        return new ResponseEntity<>("Dog breed deleted", HttpStatus.NO_CONTENT);
    }
}
