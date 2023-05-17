package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("DOG")
public class Dog extends Animal {

    @JoinColumn(name = "DOG_SHELTER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private DogShelter dogShelter;

    public Dog(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }

    public DogShelter getDogShelter() {
        return dogShelter;
    }

    public void setDogShelter(DogShelter dogShelter) {
        this.dogShelter = dogShelter;
    }
}
