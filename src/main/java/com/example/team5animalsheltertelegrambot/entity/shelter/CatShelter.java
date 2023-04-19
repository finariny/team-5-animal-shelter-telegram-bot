package com.example.team5animalsheltertelegrambot.entity.shelter;


import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue("CSH")
@Table(name = "cat_shelter")
public class CatShelter extends AnimalShelter {

    @OneToMany
    private List<Cat> cats;
}

//    //пока оставил комменты, что бы не потерять информацию
//    private String name ; //"Pussycat Home"
//
//    private String address; //"улица Бейбитшилик 67, Астана 010000, Казахстан"
//
//    private String contact;// "Телефон: +7 701 874 3939"
//
//    @Value(value = "${name.of.CatShelterSchema.data.file}")
//    private String drivingDirections;
//
//    @Value(value = "${name.of.RecommendationCatShelter.data.file}")
//    private String safetyAdvice;


