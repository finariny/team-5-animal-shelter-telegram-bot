package com.example.team5animalsheltertelegrambot.entity.person;

import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Person {

    @JoinTable(name = "ANIMAL_SHELTER_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ANIMAL_SHELTER_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<AnimalShelter> animalShelters;

    public Employee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }

    public List<AnimalShelter> getAnimalShelters() {
        return animalShelters;
    }

    public void setAnimalShelters(List<AnimalShelter> animalShelters) {
        this.animalShelters = animalShelters;
    }
}
