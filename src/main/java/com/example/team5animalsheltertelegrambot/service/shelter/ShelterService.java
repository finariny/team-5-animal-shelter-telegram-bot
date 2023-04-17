package com.example.team5animalsheltertelegrambot.service.shelter;


import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


/**
 * Сервис по работе с питомцами и редактировании их основной информации
 * @param <T> выступает объект класса питомцаСобак или питомцаКошек,
 *           который определяется в контроллерах и адресно направляет
 *           работу данных методов.
 */

public interface ShelterService <T extends AnimalShelter>{

    /**
     * Метод по редактированию названия питомца
     * @param name принимает строковое название
     * @return возвращает строку с новым названием
     */
    String updateName(T t, String name);

    /**
     * Метод по редактированию названия питомца
     * @param address принимает строковый адрес
     * @return возвращает строку с новым адресом
     */
    String updateAddress(T t, String address);

    /**
     * Метод по редактированию названия питомца
     * @param contact принимает строковые контакты
     * @return возвращает строку с новыми контактами
     */
    String updateContact(T t, String contact);


/** =Блок методов по работе с файлами схем проезда к питомцам или файлами рекомендаций для будущих хозяев животны=*/

    /**
     * Заменяет сохраненный на жестком (локальном) диске файл со схемой проезда к питомцу на новый
     * @param file .png со схемой проезда к питомцу
     */
    void importSchemaDataFile(AnimalShelter t, MultipartFile file) throws IOException;

    /**Вспомогательный класс для работы с файлами Schema*/
    File getSchemaDataFile(AnimalShelter t);

    /**
     * Удаление старого файла
     * @param filename
     * @return true - если удачно очистили\ false - если возникло исключение
     */
    boolean cleanDataFile(String filename);

    /**
     * Заменяет сохраненный на жестком (локальном) диске файл со схемой проезда к питомцу на новый
     * @param file .png со схемой проезда к питомцу
     */
    void importAdviceDataFile(AnimalShelter t, MultipartFile file) throws IOException;

    /**Вспомогательный класс для работы с файлами Advice*/
    File getAdviceDataFile(AnimalShelter t);
}
