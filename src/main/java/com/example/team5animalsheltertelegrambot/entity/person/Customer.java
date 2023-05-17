package com.example.team5animalsheltertelegrambot.entity.person;

import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@NoArgsConstructor
@DiscriminatorValue("CUSTOMER")
public class Customer extends Person {

    @Column(name = "CHAT_ID")
    private Long chatId;

    @OneToMany(mappedBy = "customer")
    private List<AnimalReport> animalReports;

    @JoinTable(name = "ANIMAL_SHELTER_CUSTOMER_LINK",
            joinColumns = @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ANIMAL_SHELTER_ID", referencedColumnName = "ID"))
    @ManyToMany
    private List<AnimalShelter> animalShelters;

    public Customer(String firstName, String lastName, @NotNull Long chatId) {
        setFirstName(firstName);
        setLastName(lastName);
        setChatId(chatId);
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public List<AnimalReport> getAnimalReports() {
        return animalReports;
    }

    public void setAnimalReports(List<AnimalReport> animalReports) {
        this.animalReports = animalReports;
    }

    public List<AnimalShelter> getAnimalShelters() {
        return animalShelters;
    }

    public void setAnimalShelters(List<AnimalShelter> animalShelters) {
        this.animalShelters = animalShelters;
    }
}
