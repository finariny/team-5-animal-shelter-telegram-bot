package com.example.team5animalsheltertelegrambot.service.shelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;


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

}
