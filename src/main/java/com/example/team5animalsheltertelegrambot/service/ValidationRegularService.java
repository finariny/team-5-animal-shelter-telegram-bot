package com.example.team5animalsheltertelegrambot.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Вспомогательный класс для валидации входящих данных с помощью регулярных выражений
 */
@Service
public class ValidationRegularService {

    private static Pattern pattern;
    private static Matcher matcher;

    //Ниже блок констант паттернов для разных проверок:



    private static final String NAME_PATTERN ="^[a-zA-Zа-яА-Я0-9]{2,16}$";
    //Для проверки имени фамилии или названия Пример: Вася / Петров1 / Ян
    // от 2 до 16 букв или цифр (или цифр убрать?)

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    //пример pank1@gmail.com

    private static final String BASE_STR_PATTERN ="^[^\\/:*?\"<>|+]+$";
    //[^\/:*?"<>|+] – знак ‘^’ в начале диапазона указывает на отрицание (в диапазон включаются все символы, кроме тех, которые написаны после ‘^’).
    // Строка должна состоять из 1 или больше букв, или цифр.
    //Пример: Документ1.pdf или "ул. Жумабека Ташенова 15, Астана 020000, Казахстан"

    private static final String TELEPHONE_PATTERN ="^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    //Проверка телефона начинающегося с +7... или 8 и размером 7 или 10 цифр соответственно

    private static final String DATE_PATTERN ="^[0-9]{4}([- /.])(((0[13578]|(10|12))\1(0[1-9]|[1-2][0-9]|3[0-1]))|(02\1(0[1-9]|[1-2][0-9]))|((0[469]|11)\1(0[1-9]|[1-2][0-9]|30)))$";
    //Формат даты ISO (гггг-мм-дд) с разделителями «-», «/» или «.». или ' '. Принудительно использует один и тот же разделитель для всех дат.

    private static final String TIME_PATTERN="^(20|21|22|23|[01]\\d|\\d)((:[0-5]\\d){1,2})$";
    //Часы и минуты, 24 hours format (HH:MM)


    //Ниже блок самих методов validate:
    public static boolean validateName(String str) {
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean validateEmail(String str) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean validateBaseStr(String str) {
        pattern = Pattern.compile(BASE_STR_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean validateTelephone(String str) {
        pattern = Pattern.compile(TELEPHONE_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean validateDate(String str) {
        pattern = Pattern.compile(DATE_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }
    public static boolean findValidatePhone(String str) {
        pattern = Pattern.compile(TELEPHONE_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.find();
    } // хотел допускать наличие в str других слов помимо телефона(но пока не смог)


    public static boolean validateTime(String str) {
        pattern = Pattern.compile(TIME_PATTERN);
        matcher = pattern.matcher(str);
        return matcher.matches();
    }




}
