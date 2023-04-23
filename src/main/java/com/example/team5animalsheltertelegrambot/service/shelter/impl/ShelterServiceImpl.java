package com.example.team5animalsheltertelegrambot.service.shelter.impl;

import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.repository.CatShelterRepository;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class ShelterServiceImpl implements ShelterService {

    @Autowired
    private static CatShelterRepository catShelterRepository;

    @Value(value = "${path.to.data.files}")
    private String dataFilePath;




//    public static String findAny(){
//        Optional<CatShelter> catShelter = catShelterRepository.findById(1);
//        if (catShelter.isPresent()) {
//            return catShelter.get().getName();
//        } else return "I have nothing to say.\n";
//    }
//    public static void main(String[] args) {
//
//        System.out.println(findAny());
//    }

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
    public String updateDescription(AnimalShelter t, String description) {
        t.setDescription(description);
        return t.getDescription();
    }

    /**
     * Внутренний метод для удаления старого файла
     * @param filename
     * @return true - если удачно очистили\ false - если возникло исключение
     */
    private boolean cleanDataFile(String filename) {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, filename));
            Files.createFile(Path.of(dataFilePath, filename));
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    /**Вспомогательный метод для работы с файлами схем проезда*/
    private File getSchemaDataFile(AnimalShelter t) {
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

    /**Вспомогательный метод для работы с файлами Advice*/
    private File getAdviceDataFile(AnimalShelter t) {
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
