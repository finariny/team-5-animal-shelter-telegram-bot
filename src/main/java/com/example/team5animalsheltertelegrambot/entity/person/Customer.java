package com.example.team5animalsheltertelegrambot.entity.person;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
@Table(name = "ast_customer")
public class Customer extends Person {

    @Getter
    @Setter
    private Long chatId;

    public Customer(String firstName, String lastName, Long chatId) {
        setFirstName(firstName);
        setLastName(lastName);
        setChatId(chatId);
    }
}
