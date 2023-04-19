package com.example.team5animalsheltertelegrambot.entity.shelter;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.person.Employee;
import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * This is superClass for Cat and Dog Shelter
 */


@Getter
@Setter
@MappedSuperclass
public abstract class AnimalShelter extends NamedEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "work_schedule")
    private String workSchedule;

    @Column(name = "driving_directions")
    private String drivingDirections;

    @Column(name = "contacts")
    private String contacts;

    @Column(name = "safety_advice")
    private String safetyAdvice;

    @Column(name = "description")
    private String description;

    @OneToMany
    private List<Customer> customers;

    @OneToMany
    private List<Employee> employees;

    @Override
    public String toString() {
        return getName();
    }

}
