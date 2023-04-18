package com.example.team5animalsheltertelegrambot.service;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.repository.AnimalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с животными
 */
@Service
public class AnimalService<T extends Animal> {

    private final AnimalRepository<T> animalRepository;

    public AnimalService(AnimalRepository<T> animalRepository) {
        this.animalRepository = animalRepository;
    }

    /**
     * Сохраняет заданную сущность.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     *
     * @param t сохраняемая сущность
     * @return сохраняемая сущность
     */
    public T save(T t) {
        if (!(t == null
                || (t.getName() == null || t.getName().isBlank())
                || (t.getAge() == null || t.getAge() < 0)
                || t.getHealthy() == null
                || t.getVaccinated() == null)) {
            return animalRepository.save(t);
        }
        return null;
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     *
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Optional<T> findById(Integer id) {
        return animalRepository.findById(id);
    }

    /**
     * Возвращает список сущностей по значению состояния здоровья.
     * Используется метод репозитория {@link AnimalRepository#findAllByHealth(Boolean)}
     *
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @return список возвращаемых сущностей
     */
    public List<T> findAllByHealth(Boolean isHealthy) {
        return animalRepository.findAllByHealth(isHealthy);
    }

    /**
     * Возвращает список сущностей по значению наличия/отсутствия вакцинации.
     * Используется метод репозитория {@link AnimalRepository#findAllByVaccination(Boolean)}
     *
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<T> findAllByVaccinate(Boolean isVaccinated) {
        return animalRepository.findAllByVaccination(isVaccinated);
    }

    /**
     * Возвращает список всех сущностей.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     *
     * @return список возвращаемых сущностей
     */
    public List<T> findAll() {
        return animalRepository.findAll();
    }

    /**
     * Обновляет сущность по передаваемым параметрам.
     * Используется метод репозитория {@link AnimalRepository#updateById(Integer, String, Integer, Boolean, Boolean)}
     *
     * @param id           идентификатор сущности
     * @param name         имя сущности
     * @param age          возраст сущности
     * @param isHealthy    состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return число ({@code 1} - сущность обновлена, {@code 0} - сущность не обновлена)
     */
    public Integer updateById(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        Optional<T> t = findById(id);
        if (t.isEmpty()
                || (name == null || name.isBlank())
                || (age == null || age < 0)
                || isHealthy == null
                || isVaccinated == null) {
            return 0;
        } else {
            animalRepository.updateById(id, name, age, isHealthy, isVaccinated);
            return 1;
        }
    }

    /**
     * Удаляет сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#deleteById(Object)}
     *
     * @param id идентификатор удаляемой сущности
     */
    public void deleteById(Integer id) {
        if (!(id == null || id < 1)) {
            animalRepository.deleteById(id);
        }
    }
}
