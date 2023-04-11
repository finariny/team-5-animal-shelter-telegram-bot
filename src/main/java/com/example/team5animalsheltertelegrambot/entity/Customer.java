package com.example.team5animalsheltertelegrambot.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "ast_customer")
public class Customer extends PersonEntity {
    public Customer(String firstName, String lastName) {
        setFirstName(firstName);
        setLastName(lastName);
    }
}
