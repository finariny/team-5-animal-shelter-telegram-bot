package com.example.team5animalsheltertelegrambot.constant;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.animal.Dog;

public class AnimalConstants {
    public static final Cat CORRECT_CAT = new Cat("Glafira", 3, true, false);
    public static final Dog CORRECT_DOG = new Dog("Bobik", 1, true, true);

    public static final Cat INCORRECT_CAT = new Cat("Glafira", 3, null, false);
    public static final Dog INCORRECT_DOG = new Dog("Bobik", null, true, true);
}
