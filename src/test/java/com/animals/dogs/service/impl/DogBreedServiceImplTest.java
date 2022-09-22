package com.animals.dogs.service.impl;

import com.animals.dogs.exception.DogBreedExistException;
import com.animals.dogs.exception.NonIdDogBreedException;
import com.animals.dogs.model.DogBreed;
import com.animals.dogs.repository.DogBreedRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
class DogBreedServiceImplTest {

    @Mock
    private DogBreedRepository dogBreedRepository;

    @InjectMocks
    private DogBreedServiceImpl dogService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldFindADogBreedById() {
        Long dogBreedIdExpected = 1L;
        DogBreed dogBreedExpected = getMockDogBreed();
        dogBreedExpected.setId(dogBreedIdExpected);

        Mockito.when(dogBreedRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dogBreedExpected));

        DogBreed dogBreedReturned = dogService.getDogBreedById(dogBreedIdExpected).get();

        verify(dogBreedRepository, times(1)).findById(Mockito.anyLong());
        Assertions.assertEquals(dogBreedExpected.getBreedName(), dogBreedReturned.getBreedName());
    }

    @Test
    void shouldNotFindADogBreedById() {
        Long dogBreedIdExpected = 1L;

        Mockito.when(dogBreedRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Optional<DogBreed> dogBreedReturned = dogService.getDogBreedById(dogBreedIdExpected);

        verify(dogBreedRepository, times(1)).findById(Mockito.anyLong());
        Assertions.assertFalse(dogBreedReturned.isPresent());
    }

    @Test
    void shouldFindADogBreedByName() {
        Long dogBreedIdExpected = 1L;
        String dogBreedNameExpected = "Golden Retriever";
        DogBreed dogBreedExpected = getMockDogBreed();
        dogBreedExpected.setId(dogBreedIdExpected);
        dogBreedExpected.setBreedName(dogBreedNameExpected);

        Mockito.when(dogBreedRepository.findByBreedName(Mockito.anyString())).thenReturn(Optional.of(dogBreedExpected));

        DogBreed dogBreedReturned = dogService.getDogBreedByName(dogBreedNameExpected).get();

        verify(dogBreedRepository, times(1)).findByBreedName(Mockito.anyString());
        Assertions.assertEquals(dogBreedExpected.getBreedName(), dogBreedReturned.getBreedName());
    }

    @Test
    void shouldNotFindADogBreedByName() {
        Long dogBreedIdExpected = 1L;
        String dogBreedNameExpected = "Golden Retriever";

        Mockito.when(dogBreedRepository.findByBreedName(Mockito.anyString())).thenReturn(Optional.empty());

        Optional<DogBreed> dogBreedReturned = dogService.getDogBreedByName(dogBreedNameExpected);

        verify(dogBreedRepository, times(1)).findByBreedName(Mockito.anyString());
        Assertions.assertFalse(dogBreedReturned.isPresent());
    }

    @Test
    void shouldFindADogBreedsSmallerThan() {
        Long expectedHeight = 50L;
        DogBreed dogBreedExpected = getMockDogBreed();
        List<DogBreed> dogBreedList = new ArrayList<>();
        dogBreedList.add(dogBreedExpected);

        Mockito.when(dogBreedRepository.findByHeightLessThan(Mockito.anyLong())).thenReturn(dogBreedList);

        List<DogBreed> dogBreedReturned = dogService.getDogBreedSmallerThan(expectedHeight);

        verify(dogBreedRepository, times(1)).findByHeightLessThan(Mockito.anyLong());
        Assertions.assertTrue(dogBreedExpected.getHeight() <= expectedHeight);
    }

    @Test
    void shouldSaveDogBreed() throws DogBreedExistException {
        DogBreed dogBreedToSave = getMockDogBreed();
        DogBreed dogBreedSaved = getMockDogBreed();
        dogBreedSaved.setId(1L);

        Mockito.when(dogBreedRepository.save(Mockito.any(DogBreed.class))).thenReturn(dogBreedSaved);

        DogBreed dogBreedReturned = dogService.saveDogBreed(dogBreedToSave);

        verify(dogBreedRepository, times(1)).save(Mockito.any(DogBreed.class));
        Assertions.assertNotNull(dogBreedReturned.getId());
    }

    @Test
    void shouldNotSaveDogBreedBecauseOfItHasAnId() throws DogBreedExistException {
        DogBreed dogBreedToSave = getMockDogBreed();
        dogBreedToSave.setId(1L);

        verify(dogBreedRepository, times(0)).save(Mockito.any(DogBreed.class));
        Assertions.assertThrows(DogBreedExistException.class, ()-> dogService.saveDogBreed(dogBreedToSave));
    }

    @Test
    void shouldUpdateDogBreed() throws NonIdDogBreedException {
        DogBreed dogBreedToSave = getMockDogBreed();
        DogBreed dogBreedSaved = getMockDogBreed();
        dogBreedToSave.setId(1L);
        dogBreedSaved.setId(1L);

        Mockito.when(dogBreedRepository.save(Mockito.any(DogBreed.class))).thenReturn(dogBreedSaved);

        DogBreed dogBreedReturned = dogService.updateDogBreed(dogBreedToSave);

        verify(dogBreedRepository, times(1)).save(Mockito.any(DogBreed.class));
        Assertions.assertNotNull(dogBreedToSave.getId());
        Assertions.assertNotNull(dogBreedReturned.getId());
    }

    @Test
    void shouldNotUpdateDogBreedBecauseOfNullId() throws DogBreedExistException {
        DogBreed dogBreedToSave = getMockDogBreed();

        verify(dogBreedRepository, times(0)).save(Mockito.any(DogBreed.class));
        Assertions.assertThrows(NonIdDogBreedException.class, ()-> dogService.updateDogBreed(dogBreedToSave));
    }

    @Test
    void shouldDeleteDogBreed() throws DogBreedExistException {
        dogService.deleteDogBreed(1L);
        verify(dogBreedRepository, times(1)).deleteById(Mockito.anyLong());
    }

    private DogBreed getMockDogBreed() {
        return DogBreed.builder()
                .breedName("golden retriever")
                .description("A nice dog")
                .height(50L)
                .weight(45.0)
                .pictureURL("www")
                .lifeSpan("8 - 14")
                .build();
    }

}