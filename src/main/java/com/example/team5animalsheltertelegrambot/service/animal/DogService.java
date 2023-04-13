package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.repository.animal.DogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сущностями dog
 */
@Service
public class DogService {

    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * Сохраняет заданную сущность.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param dog сохраняемая сущность
     * @return сохраняемая сущность
     */
    public Dog save(Dog dog) {
        if (dog != null) {
            return dogRepository.save(dog);
        }
        return null;
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Optional<Dog> findById(Integer id) {
        return dogRepository.findById(id);
    }

    /**
     * Возвращает список сущностей по состоянию здоровья.
     * Используется метод репозитория {@link DogRepository#findAllByHealthy(Boolean)}
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAllByHealth(Boolean isHealthy) {
        return dogRepository.findAllByHealthy(isHealthy);
    }

    /**
     * Возвращает список сущностей по наличию/отсутствию вакцинации.
     * Используется метод репозитория {@link DogRepository#findAllByVaccinated(Boolean)}
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAllByVaccinate(Boolean isVaccinated) {
        return dogRepository.findAllByVaccinated(isVaccinated);
    }

    /**
     * Возвращает список всех сущностей.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    /**
     * Обновляет сущность по передаваемым параметрам.
     * Используется метод репозитория {@link DogRepository#updateById(Integer, String, Integer, Boolean, Boolean)}
     * @param id идентификатор сущности
     * @param name имя сущности
     * @param age возраст сущности
     * @param isHealthy состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return обновляемая сущность
     */
    public Dog updateBuId(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        if (id != null && name != null && age != null && isHealthy != null && isVaccinated != null) {
            return dogRepository.updateById(id, name, age, isHealthy, isVaccinated);
        }
        return null;
    }

    /**
     * Удаляет сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     * @param id идентификатор удаляемой сущности
     */
    public void deleteById(Integer id) {
        if (id != null) {
            dogRepository.deleteById(id);
        }
    }
}
