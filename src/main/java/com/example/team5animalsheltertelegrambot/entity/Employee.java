package com.example.team5animalsheltertelegrambot.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "ast_employee")
public class Employee extends PersonEntity {
    public Employee(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
}
