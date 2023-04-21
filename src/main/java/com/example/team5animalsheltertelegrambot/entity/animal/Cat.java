package com.example.team5animalsheltertelegrambot.entity.animal;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "CAT")
@Table(name = "cat")
public class Cat extends Animal{

    public Cat(String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setName(name);
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }
}
