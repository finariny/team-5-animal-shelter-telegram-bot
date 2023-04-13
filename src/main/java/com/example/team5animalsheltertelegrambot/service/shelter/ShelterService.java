package com.example.team5animalsheltertelegrambot.service.shelter;


import com.example.team5animalsheltertelegrambot.entity.Shelter.AnimalShelter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ShelterService <T extends AnimalShelter>{

    String updateName(T t, String name);
    String updateAddress(T t, String address);

    String updateContact(T t, String contact);

    String updateDirections(AnimalShelter t, String directions);

    String updateSafetyAdvice(AnimalShelter t, String safetyAdvice);


    File getDogShelterSchemaDataFile();

    void importDogShelterSchemaDataFile(MultipartFile file) throws IOException;

    boolean cleanDataFile(String filename);
}
