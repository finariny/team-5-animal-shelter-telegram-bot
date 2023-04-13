package com.example.team5animalsheltertelegrambot.entity.Shelter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;




@Data
@NoArgsConstructor
@AllArgsConstructor

public class CatShelter extends AnimalShelter{

    private String name = "Pussycat Home";
    private String address = "улица Бейбитшилик 67, Астана 010000, Казахстан";
    @Value(value = "PussyCatSchema.bmp")
    private String drivingDirections = "src/main/resources/ShelterData/PussyCatSchema.bmp";
    private String contact = "Телефон: +7 701 874 3939";
    @Value(value = "PussyCatSchema.bmp")
    private String safetyAdvice="src/main/resources/ShelterData/RecommendationCatShelter.pdf";
}
