package com.example.team5animalsheltertelegrambot.entity.animal;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "dog")
public class Dog extends Animal{

    public Dog(Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }
}
