package com.example.team5animalsheltertelegrambot.constant;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.animal.Dog;

import java.util.List;

public class AnimalConstants {
    public static final Cat CORRECT_CAT_1 = new Cat("Glafira", 3, true, false);
    public static final Cat CORRECT_CAT_2 = new Cat("Sofia", 1, true, false);
    public static final Cat CORRECT_CAT_3 = new Cat("Boris", 4, false, true);
    public static final Cat CORRECT_CAT_4 = new Cat("Yasha", 1, false, false);

    public static final Dog CORRECT_DOG_1 = new Dog("Bobik", 1, true, true);
    public static final Dog CORRECT_DOG_2 = new Dog("Jack", 3, true, false);
    public static final Dog CORRECT_DOG_3 = new Dog("Kesha", 7, true, false);
    public static final Dog CORRECT_DOG_4 = new Dog("Semen", 5, false, true);

    public static final List<Cat> LIST_OF_CORRECT_CATS = List.of(CORRECT_CAT_1, CORRECT_CAT_2, CORRECT_CAT_3, CORRECT_CAT_4);
    public static final List<Dog> LIST_OF_CORRECT_DOGS = List.of(CORRECT_DOG_1, CORRECT_DOG_2, CORRECT_DOG_3, CORRECT_DOG_4);

    public static final List<Cat> LIST_OF_HEALTHY_AND_UNVACCINATED_CATS = List.of(CORRECT_CAT_1, CORRECT_CAT_2);

    public static final List<Cat> LIST_OF_HEALTHY_CATS = List.of(CORRECT_CAT_1, CORRECT_CAT_2);
    public static final List<Cat> LIST_OF_UNHEALTHY_CATS = List.of(CORRECT_CAT_3, CORRECT_CAT_4);

    public static final List<Cat> LIST_OF_VACCINATED_CATS = List.of(CORRECT_CAT_3);
    public static final List<Cat> LIST_OF_UNVACCINATED_CATS = List.of(CORRECT_CAT_1, CORRECT_CAT_2, CORRECT_CAT_4);

    public static final List<Dog> LIST_OF_HEALTHY_DOGS = List.of(CORRECT_DOG_1, CORRECT_DOG_2, CORRECT_DOG_3);
    public static final List<Dog> LIST_OF_UNHEALTHY_DOGS = List.of(CORRECT_DOG_4);

    public static final List<Dog> LIST_OF_VACCINATED_DOGS = List.of(CORRECT_DOG_1, CORRECT_DOG_4);
    public static final List<Dog> LIST_OF_UNVACCINATED_DOGS = List.of(CORRECT_DOG_2, CORRECT_DOG_3);

    public static final Cat INCORRECT_CAT = new Cat("Glafira", 3, null, false);
    public static final Dog INCORRECT_DOG = new Dog("Bobik", null, true, true);
}
