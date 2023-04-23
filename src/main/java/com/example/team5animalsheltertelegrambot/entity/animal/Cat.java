package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "CAT")
@Table(name = "cat")
public class Cat extends Animal{

    @ManyToOne
    @JoinColumn(name = "cats", referencedColumnName = "id")
    private CatShelter catShelter;

    public Cat(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }
}
