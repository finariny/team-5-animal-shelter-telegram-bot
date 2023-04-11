package com.example.team5animalsheltertelegrambot.entity.Shelter;

import com.example.team5animalsheltertelegrambot.entity.BaseEntity;
import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.MappedSuperclass;

/**
 * This is abstract superClass for Cat and Dog Shelter
 */
@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AnimalShelter extends NamedEntity {
    private String address;
    private String workSchedule;
    private String drivingDirections;
    private String contacts;
    private String safetyAdvice;

}
