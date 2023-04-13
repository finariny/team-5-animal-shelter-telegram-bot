package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.repository.animal.DogRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog save(Dog dog) {
        if (dog != null) {
            return dogRepository.save(dog);
        }
        return null;
    }

    public Optional<Dog> findById(Integer id) {
        return dogRepository.findById(id);
    }

    public List<Dog> findAllByHealth(Boolean isHealthy) {
        return dogRepository.findAllByHealthy(isHealthy);
    }

    public List<Dog> findAllByVaccinate(Boolean isVaccinated) {
        return dogRepository.findAllByVaccinated(isVaccinated);
    }

    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    public Dog updateBuId(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        if (id != null && name != null && age != null && isHealthy != null && isVaccinated != null) {
            return dogRepository.updateById(id, name, age, isHealthy, isVaccinated);
        }
        return null;
    }

    public void deleteById(Integer id) {
        if (id != null) {
            dogRepository.deleteById(id);
        }
    }
}
