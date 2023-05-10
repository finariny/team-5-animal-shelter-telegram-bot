package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.animal.DogRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с собаками
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
     *
     * @param dog сохраняемая сущность
     * @return {@code true} - сущность сохранена, {@code false} - сущность не сохранена
     */
    public boolean save(Dog dog) {
        if (!(dog == null
                || (dog.getName() == null || dog.getName().isBlank())
                || (dog.getAge() == null || dog.getAge() < 0)
                || dog.getHealthy() == null
                || dog.getVaccinated() == null)) {
            dogRepository.save(dog);
            return true;
        }
        return false;
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Optional<Dog> findById(Integer id) {
        return dogRepository.findById(id);
    }

    /**
     * Возвращает список сущностей по значению состояния здоровья.
     * Используется метод репозитория {@link DogRepository#findAllByHealth(Boolean)}
     *
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAllByHealth(Boolean isHealthy) {
        return dogRepository.findAllByHealth(isHealthy);
    }

    /**
     * Возвращает список сущностей по значению наличия/отсутствия вакцинации.
     * Используется метод репозитория {@link DogRepository#findAllByVaccination(Boolean)}
     *
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAllByVaccinate(Boolean isVaccinated) {
        return dogRepository.findAllByVaccination(isVaccinated);
    }

    /**
     * Возвращает список сущностей по значению состояния здоровья и/или значению наличия/отсутствия вакцинации.
     * Используется метод репозитория {@link DogRepository#findAllByHealthAndVaccination(Boolean, Boolean)}
     *
     * @param isHealthy    состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAllByHealthAndVaccination(Boolean isHealthy, Boolean isVaccinated) {
        return dogRepository.findAllByHealthAndVaccination(isHealthy, isVaccinated);
    }

    /**
     * Возвращает список всех сущностей.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     *
     * @return список возвращаемых сущностей
     */
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    /**
     * Обновляет сущность по передаваемым параметрам.
     * Используется метод репозитория {@link DogRepository#updateById(Integer, String, Integer, Boolean, Boolean)}
     *
     * @param id           идентификатор сущности
     * @param name         имя сущности
     * @param age          возраст сущности
     * @param isHealthy    состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return число ({@code 1} - сущность обновлена, {@code 0} - сущность не обновлена)
     */
    public Integer updateById(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        Optional<Dog> dog = findById(id);
        if (dog.isEmpty()
                || (name == null || name.isBlank())
                || (age == null || age < 0)
                || isHealthy == null
                || isVaccinated == null) {
            return 0;
        } else {
            dogRepository.updateById(id, name, age, isHealthy, isVaccinated);
            return 1;
        }
    }

    /**
     * Удаляет сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     *
     * @param id идентификатор удаляемой сущности
     * @return {@code true} - сущность сохранена, {@code false} - сущность не сохранена
     */
    public Boolean deleteById(Integer id) {
        Optional<Dog> findDogById = findById(id);
        if (id == null || findDogById.isEmpty()) {
            return false;
        }
        dogRepository.deleteById(id);
        return true;
    }

    /**
     * Регистрирует усыновление собаки посетителем, добавляет текущую дату регистрации
     *
     * @param dog собака
     * @param customer посетитель
     * @return {@code true} - сущность обновлена, {@code false} - сущность не обновлена
     */
    public boolean adopt(@NotNull Dog dog, @NotNull Customer customer) {
        dog.setAdopter(customer);
        dog.setDateAdoption(LocalDateTime.now());
        return this.save(dog);
    }
}
