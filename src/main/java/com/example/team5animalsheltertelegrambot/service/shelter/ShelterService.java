package com.example.team5animalsheltertelegrambot.service.shelter;


import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


/**
 * Сервис по работе с питомцами и редактировании их основной информации
 * @param <T> выступает объект класса питомцаСобак или питомцаКошек,
 *           который определяется в контроллерах и адресно направляет
 *           работу данных методов.
 */

public interface ShelterService <T extends AnimalShelter> {





    /**
     * Метод по редактированию названия приюта
     * @param name принимает строковое название
     * @return возвращает строку с новым названием
     */
    String updateName(AnimalShelter t, String name);

    /**
     * Метод по редактированию адреса приюта
     * @param address принимает строковый адрес
     * @return возвращает строку с новым адресом
     */
    String updateAddress(AnimalShelter t, String address);

    /**
     * Метод по редактированию контактов приюта
     * @param contact принимает строковые контакты
     * @return возвращает строку с новыми контактами
     */
    String updateContact(AnimalShelter t, String contact);

    /**
     * Метод по редактированию описания приюта
     * @param description принимает строковое описание
     * @return возвращает строку с новым описанием
     */
    String updateDescription(AnimalShelter t, String description);



/** =Блок методов по работе с файлами схем проезда к приюту или файлами рекомендаций для будущих хозяев животных=*/
    /**
     * Заменяет сохраненный на жестком (локальном) диске файл со схемой проезда к приюту на новый
     * @param file .png со схемой проезда к приюту
     */
    void importSchemaDataFile(AnimalShelter t, MultipartFile file) throws IOException;

    /**
     * Заменяет сохраненный на жестком (локальном) диске файл со схемой проезда к приюту на новый
     * @param file .png со схемой проезда к приюту
     */
    void importAdviceDataFile(AnimalShelter t, MultipartFile file) throws IOException;

//    Дублируется с Animal Service
//    /**
//     * Метод находит всех животных в приюте
//     * @param r - должно выступать CatShelterRepository или DogShelterRepository
//     * @return список животных
//     */
//    List<T> findAllAnimalsInShelter(R r);


}
