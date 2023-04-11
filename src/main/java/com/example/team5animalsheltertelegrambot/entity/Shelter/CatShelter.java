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
@Table(name = "cat_shelter")
public class CatShelter extends AnimalShelter{
    @Column(name = "shelter_name")
    private final String name = "Pussycat Home";
    @Column(name = "address")
    private final String address = "улица Бейбитшилик 67, Астана 010000, Казахстан";
    @Column(name = "driving_directions")
    private final String drivingDirections = "src/main/resources/ShelterData/PussyCatSchema.bmp";
    @Column(name = "contact")
    private final String contact = "Телефон: +7 701 874 3939";
    @Column(name = "safety_advice")
    private final String safetyAdvice="src/main/resources/ShelterData/RecommendationCatShelter.pdf";
}
