package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CAT")
public class Cat extends Animal {

    @JoinColumn(name = "CAT_SHELTER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private CatShelter catShelter;

    public Cat(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }

    public CatShelter getCatShelter() {
        return catShelter;
    }

    public void setCatShelter(CatShelter catShelter) {
        this.catShelter = catShelter;
    }
}
