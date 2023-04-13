package com.example.team5animalsheltertelegrambot.entity;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "animal_report")
public class AnimalReport extends NamedEntity {
    @Column(name = "photo")
    private String photo;
    @Column(name = "diet")
    private String diet;
    @Column(name = "well_being")
    private String wellBeing;
    @Column(name = "behavior")
    private String behavior;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @ManyToOne
    @Column(name = "customer")
    private Customer customer;
    @ManyToOne
    private Animal animal;
}
