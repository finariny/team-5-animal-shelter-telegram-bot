package com.example.team5animalsheltertelegrambot.entity.report;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ANIMAL_REPORT", indexes = {
        @Index(name = "IDX_ANIMAL_REPORT_ANIMAL", columnList = "ANIMAL_ID"),
        @Index(name = "IDX_ANIMAL_REPORT_CUSTOMER", columnList = "CUSTOMER_ID")
})
public class AnimalReport extends NamedEntity {

    @Column(name = "PHOTO")
    private String photo;

    @Column(name = "DIET")
    private String diet;

    @Column(name = "WELL_BEING")
    private String wellBeing;

    @Column(name = "BEHAVIOR")
    private String behavior;

    @Column(name = "DATE_CREATE")
    private LocalDateTime dateCreate;

    @JoinColumn(name = "ANIMAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Animal animal;

    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellBeing() {
        return wellBeing;
    }

    public void setWellBeing(String wellBeing) {
        this.wellBeing = wellBeing;
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
