package com.example.team5animalsheltertelegrambot.entity.shelter;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is abstract superClass for Cat and Dog Shelter
 */


@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class AnimalShelter extends NamedEntity {
    private String address;
    private String workSchedule;
    private String drivingDirections;
    private String contacts;
    private String safetyAdvice;

}
