package com.example.team5animalsheltertelegrambot.entity.Shelter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;



@Data
@NoArgsConstructor
public class DogShelter extends AnimalShelter{
    private final String name = "Your true friend";
    private final String address = "ул. Жумабека Ташенова 15, Астана 020000, Казахстан";
    private final String drivingDirections = "src/main/resources/ShelterData/TrueFriendSchema.bmp";
    private final String contact = "Телефон: +7 775 787 2065";
    private final String safetyAdvice= "src/main/resources/ShelterData/RecommendationDogShelter.pdf";
}
