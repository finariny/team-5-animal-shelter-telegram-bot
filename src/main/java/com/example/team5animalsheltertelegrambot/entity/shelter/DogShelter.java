package com.example.team5animalsheltertelegrambot.entity.shelter;


import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Setter
@DiscriminatorValue("DSH")
@Table(name = "dog_shelter")
public class DogShelter extends AnimalShelter {

 @OneToMany
 private List<Dog> dogs;
}
//    //пока оставил комменты, что бы не потерять информацию
//    private String name;// "Your true friend"
//
//    private String address; // "ул. Жумабека Ташенова 15, Астана 020000, Казахстан"
//
//    private String contact; // "Телефон: +7 775 787 2065"
//
//    @Value(value = "${name.of.DogShelterSchema.data.file}")
//    private String drivingDirections;
//
//    @Value(value = "${name.of.RecommendationDogShelter.data.file}")
//    private String safetyAdvice;




