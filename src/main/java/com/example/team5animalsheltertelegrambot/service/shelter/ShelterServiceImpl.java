package com.example.team5animalsheltertelegrambot.service.shelter;

import com.example.team5animalsheltertelegrambot.entity.Shelter.AnimalShelter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ShelterServiceImpl<T extends AnimalShelter> implements ShelterService {
    @Value(value = "${path.to.data.files}")
    private String dataFilePath;
    @Value(value = "${name.of.DogShelterSchema.data.file}")
    private String getDogShelterSchemaFileName;
    @Value(value = "${name.of.CatShelterSchema.data.file}")
    private String getCatShelterSchemaFileName;
    @Value(value = "${name.of.RecommendationDogShelter.data.file}")
    private String getRecommendationDogShelterFileName;
    @Value(value = "${name.of.RecommendationCatShelter.data.file}")
    private String getRecommendationCatShelterFileName;


    @Override
    public String updateName(AnimalShelter t, String name) {
        t.setName(name);
        return t.getName();
    }

    @Override
    public String updateAddress(AnimalShelter t, String address) {
        t.setAddress(address);
        return t.getAddress();
    }

    @Override
    public String updateContact(AnimalShelter t, String contact) {
        t.setContacts(contact);
        return t.getContacts();
    }

    @Override
    public String updateDirections(AnimalShelter t, String directions) {
        t.setDrivingDirections(directions);
        return t.getDrivingDirections();
    }

    @Override
    public String updateSafetyAdvice(AnimalShelter t, String safetyAdvice) {
        t.setSafetyAdvice(safetyAdvice);
        return t.getSafetyAdvice();
    }

    @Override
    public File getDogShelterSchemaDataFile() {
        return new File(dataFilePath + "/" + getDogShelterSchemaFileName);
    }

    @Override
    public void importDogShelterSchemaDataFile(MultipartFile file) throws IOException {
        cleanDataFile(getDogShelterSchemaFileName);//удаляем дата файл и создаем пустой новый
        File dataFile = getDogShelterSchemaDataFile();// в новый берем информацию из DogShelterSchemaDataFile
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {  // открываем исходящий поток
            IOUtils.copy(file.getInputStream(), fos); // берем входящий поток из @RequestParam и копируем в исходящий поток  'fos'
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public boolean cleanDataFile(String filename) {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, filename));
            Files.createFile(Path.of(dataFilePath, filename));
            return true;
        } catch (IOException e) {
            return false;
        }


    }
}
