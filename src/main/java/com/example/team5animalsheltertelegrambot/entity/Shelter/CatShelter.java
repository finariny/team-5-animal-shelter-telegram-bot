package com.example.team5animalsheltertelegrambot.entity.Shelter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatShelter extends AnimalShelter{
    private final String name = "Pussycat Home";
    private final String address = "улица Бейбитшилик 67, Астана 010000, Казахстан";
    private final String drivingDirections = "src/main/resources/ShelterData/PussyCatSchema.bmp";
    private final String contact = "Телефон: +7 701 874 3939";
    private final String safetyAdvice="src/main/resources/ShelterData/RecommendationCatShelter.pdf";
}
