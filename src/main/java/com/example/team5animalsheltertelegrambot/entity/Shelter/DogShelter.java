package com.example.team5animalsheltertelegrambot.entity.Shelter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dog_shelter")
public class DogShelter extends AnimalShelter{
    @Column(name = "shelter_name")
    private final String name = "Your true friend";
    @Column(name = "address")
    private final String address = "ул. Жумабека Ташенова 15, Астана 020000, Казахстан";
    @Column(name = "driving_directions")
    private final String drivingDirections = "src/main/resources/ShelterData/TrueFriendSchema.bmp";
    @Column(name = "contact")
    private final String contact = "Телефон: +7 775 787 2065";
    @Column(name = "safety_advice")
    private final String safetyAdvice= "src/main/resources/ShelterData/RecommendationDogShelter.pdf";
}
