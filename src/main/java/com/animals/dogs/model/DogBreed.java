package com.animals.dogs.model;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank(message = "You must provide a breed name")
    @Column(name = "breed_name")
    private String breedName;

    private String description;

    @Column(name = "picture_URL")
    private String pictureURL;

    @NotNull(message = "You must provide a height in cm")
    private Long height;

    @NotNull(message = "You must provide a weight in kg")
    private Double weight;

    @NotBlank(message = "You must provide a life span like: '8 - 12 years'")
    @Column(name = "life_span")
    private String lifeSpan;

    @ElementCollection
    private List<String> characteristics = new ArrayList<>();
}
