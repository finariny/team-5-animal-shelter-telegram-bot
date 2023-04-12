package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public Cat save(Cat cat) {
        if (cat != null) {
            return catRepository.save(cat);
        }
        return null;
    }

    public Optional<Cat> findById(Integer id) {
        return catRepository.findById(id);
    }

    public List<Cat> findAllByHealth(Boolean isHealthy) {
        return catRepository.findAllByHealthy(isHealthy);
    }

    public List<Cat> findAllByVaccinate(Boolean isVaccinated) {
        return catRepository.findAllByVaccinated(isVaccinated);
    }

    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    public Cat updateBuId(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        if (id != null && name != null && age != null && isHealthy != null && isVaccinated != null) {
            return catRepository.updateById(id, name, age, isHealthy, isVaccinated);
        }
        return null;
    }

    public void deleteById(Integer id) {
        if (id != null) {
            catRepository.deleteById(id);
        }
    }
}
