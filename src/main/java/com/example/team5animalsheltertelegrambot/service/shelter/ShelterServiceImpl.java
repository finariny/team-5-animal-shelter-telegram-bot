package com.example.team5animalsheltertelegrambot.service.shelter;

import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
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
    public boolean cleanDataFile(String filename) {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, filename));
            Files.createFile(Path.of(dataFilePath, filename));
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    @Override
    public File getSchemaDataFile(AnimalShelter t) {
        return new File(dataFilePath + "/" + t.getDrivingDirections());
    }

    @Override
    public void importSchemaDataFile(AnimalShelter t, MultipartFile file) throws IOException {
        cleanDataFile(t.getDrivingDirections());//удаляем дата файл и создаем пустой новый
        File dataFile = getSchemaDataFile(t);// в новый берем информацию из SchemaDataFile
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {  // открываем исходящий поток
            IOUtils.copy(file.getInputStream(), fos); // берем входящий поток из @RequestParam и копируем в исходящий поток  'fos'
        } catch (IOException e) {
            throw new IOException();
        }
    }


    @Override
    public File getAdviceDataFile(AnimalShelter t) {
        return new File(dataFilePath + "/" + t.getSafetyAdvice());
    }

    @Override
    public void importAdviceDataFile(AnimalShelter t, MultipartFile file) throws IOException {
        cleanDataFile(t.getSafetyAdvice());
        File dataFile = getAdviceDataFile(t);
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
        } catch (IOException e) {
            throw new IOException();
        }
    }

}
