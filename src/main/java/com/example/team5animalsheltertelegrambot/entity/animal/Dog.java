package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "DOG")
@Table(name = "dog")
public class Dog extends Animal{

    @ManyToOne
    @JoinColumn(name = "dogs", referencedColumnName = "id")
    private DogShelter dogShelter;

    public Dog(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }
}
