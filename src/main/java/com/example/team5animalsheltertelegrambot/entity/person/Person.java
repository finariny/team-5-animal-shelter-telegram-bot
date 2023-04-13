package com.example.team5animalsheltertelegrambot.entity.person;

import com.example.team5animalsheltertelegrambot.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "ast_person")
@DiscriminatorValue("PERSON")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Person extends BaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String login;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return getLastName() + " " + getFirstName();
    }
}
