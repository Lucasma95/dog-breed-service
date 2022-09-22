package com.animals.dogs.controller;

import com.animals.dogs.exception.DogBreedExistException;
import com.animals.dogs.exception.NonIdDogBreedException;
import com.animals.dogs.model.DogBreed;
import com.animals.dogs.service.DogBreedService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @ApiOperation(value = "Get a dog breed by id", notes = "Returns a dog breed or null if it's not exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The dog breed was not found")
    })
    @GetMapping("/id/{dogBreedId}")
    public ResponseEntity<DogBreed> getDogBreedById (@PathVariable("dogBreedId") Long dogBreedId) {
        Optional<DogBreed> dog = dogBreedService.getDogBreedById(dogBreedId);
        return dog.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Get a dog breed by name", notes = "Returns a dog breed or null if it's not exist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved"),
            @ApiResponse(code = 404, message = "Not found - The dog breed was not found")
    })
    @GetMapping("/breedName/{breedName}")
    public ResponseEntity<DogBreed> getDogBreedByName (@PathVariable("breedName") String breedName) {
        Optional<DogBreed> dog = dogBreedService.getDogBreedByName(breedName);
        return dog.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Get a list dog breeds with a height equals or less than required"
            , notes = "Returns an empty list if no ones matches")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved, even when the list is empty")
    })
    @GetMapping("/maxHeight/{maxHeight}")
    public ResponseEntity<List<DogBreed>> GetDogBreedByMaxHeight (@PathVariable("maxHeight") Long maxHeight) {
        List<DogBreed> dogBreeds = dogBreedService.getDogBreedSmallerThan(maxHeight);
        return new ResponseEntity<>(dogBreeds, HttpStatus.OK);
    }

    @ApiOperation(value = "Save dog breed", notes = "In order to save it you must pass the validations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved"),
            @ApiResponse(code = 400, message = "With null attributes or non null id")
    })
    @PostMapping("/save")
    public ResponseEntity<DogBreed> saveDogBreed (@RequestBody @Valid DogBreed dogBreed) {
        DogBreed dogBreedSaved;
        try {
            dogBreedSaved = dogBreedService.saveDogBreed(dogBreed);
        } catch (DogBreedExistException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(dogBreedSaved, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update dog breed", notes = "In order to update it you must pass the validations")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated"),
            @ApiResponse(code = 400, message = "With null attributes or null id")
    })
    @PutMapping("/update")
    public ResponseEntity<DogBreed> updateDog (@RequestBody @Valid DogBreed dogBreed) {
        DogBreed dogBreedUpdated;
        try {
            dogBreedUpdated = dogBreedService.updateDogBreed(dogBreed);
        } catch (NonIdDogBreedException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(dogBreedUpdated, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete dog breed", notes = "Always return 204, even if it didn't delete a dog breed")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Does not include message because of 204 code response")
    })
    @DeleteMapping("/delete/{dogBreedId}")
    public ResponseEntity<String> deleteDog(@PathVariable("dogBreedId") Long dogBreedId) {
        dogBreedService.deleteDogBreed(dogBreedId);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
