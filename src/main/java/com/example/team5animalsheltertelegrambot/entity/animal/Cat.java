package com.example.team5animalsheltertelegrambot.entity.animal;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "cat")
public class Cat extends Animal{

    public Cat(Integer age, Boolean isHealthy, Boolean isVaccinated) {
        setAge(age);
        setHealthy(isHealthy);
        setVaccinated(isVaccinated);
    }
}
