package com.example.team5animalsheltertelegrambot.constant;

import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

public class ShelterConstants {
    public static final String CORRECT_NAME =  "Your true friend";
    public static final String CORRECT_ADDRESS = "ул. Жумабека Ташенова 15, Астана 020000, Казахстан";
    public static final String CORRECT_CONTACT =  "Телефон: +7 775 787 2065";
    public static final String DEFAULT_NAME = "Your true friend";
    public static final String DEFAULT_ADDRESS = "ул. Жумабека Ташенова 15, Астана 020000, Казахстан";
    public static final String DEFAULT_CONTACT = "Телефон: +7 775 787 2065";

    public static final MediaType MEDIA_TYPE_PNG = new MediaType(IMAGE_PNG_VALUE);
    public static final MediaType MEDIA_TYPE_PDF = new MediaType(APPLICATION_PDF_VALUE);


}
