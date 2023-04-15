package com.example.team5animalsheltertelegrambot.entity.shelter;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class CatShelter extends AnimalShelter{

    //НАДО добавить инициализацию Строк Питомца! .. пока оставил комменты, что бы не потерять информацию
    private String name ; //"Pussycat Home"

    private String address; //"улица Бейбитшилик 67, Астана 010000, Казахстан"

    private String contact;// "Телефон: +7 701 874 3939"


    //Товарищи! ) Правильно ли подтянется информация из application.properties?
    @Value(value = "${name.of.CatShelterSchema.data.file}")
    private String drivingDirections;

    @Value(value = "${name.of.RecommendationCatShelter.data.file}")
    private String safetyAdvice;
}
