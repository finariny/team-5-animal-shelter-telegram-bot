package com.example.team5animalsheltertelegrambot.entity.shelter;


import com.example.team5animalsheltertelegrambot.entity.animal.Dog;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("DSH")
public class DogShelter extends AnimalShelter {

    @OneToMany(mappedBy = "dogShelter")
    private List<Dog> dogs;

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
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




