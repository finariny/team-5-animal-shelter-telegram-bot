package com.example.team5animalsheltertelegrambot.service.shelter.impl;
import com.example.team5animalsheltertelegrambot.entity.shelter.AnimalShelter;
import com.example.team5animalsheltertelegrambot.service.shelter.ShelterService;
import org.springframework.stereotype.Service;


@Service
public class ShelterServiceImpl implements ShelterService {

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


}
