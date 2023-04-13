package com.example.team5animalsheltertelegrambot.entity.person;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@DiscriminatorValue("EMPLOYEE")
@Table(name = "ast_employee")
public class Employee extends Person {

    public Employee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
}
