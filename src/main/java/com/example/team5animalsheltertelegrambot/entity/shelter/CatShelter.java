package com.example.team5animalsheltertelegrambot.entity.shelter;


import com.example.team5animalsheltertelegrambot.entity.animal.Cat;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@DiscriminatorValue("KSH")
public class CatShelter extends AnimalShelter {

    @OneToMany(mappedBy = "catShelter")
    private List<Cat> cats;

    public List<Cat> getCats() {
        return cats;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }
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


