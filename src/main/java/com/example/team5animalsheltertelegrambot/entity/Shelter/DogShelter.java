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
@AllArgsConstructor
public class DogShelter extends AnimalShelter{
    private String name = "Your true friend";
    private String address = "ул. Жумабека Ташенова 15, Астана 020000, Казахстан";
    private String drivingDirections = "src/main/resources/ShelterData/TrueFriendSchema.bmp";
    private String contact = "Телефон: +7 775 787 2065";
    private String safetyAdvice= "src/main/resources/ShelterData/RecommendationDogShelter.pdf";

}
