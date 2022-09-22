package com.animals.dogs.repository;

import com.animals.dogs.model.DogBreed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogBreedRepository extends JpaRepository<DogBreed, Long> {

    Optional<DogBreed> findByBreedName(String breedName);

    @Query("SELECT d FROM DogBreed d WHERE d.height <= :maxHeight")
    List<DogBreed> findDogsSmallerThan(@Param("maxHeight") Double maxHeight);

    List<DogBreed> findByHeightLessThan(@Param("maxHeight") Long maxHeight);
}
