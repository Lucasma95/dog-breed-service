package com.animals.dogs.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "dog_breed")
public class DogBreed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "breed_name")
    private String breedName;

    private String description;

    @Column(name = "picture_URL")
    private String pictureURL;

    @NonNull
    private Long height;

    @NonNull
    private Double weight;

    @NonNull
    @Column(name = "life_span")
    private String lifeSpan;

    @ElementCollection
    private List<String> characteristics = new ArrayList<>();
}
