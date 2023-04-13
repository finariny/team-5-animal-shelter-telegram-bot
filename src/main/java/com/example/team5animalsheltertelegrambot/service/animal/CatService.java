package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с сущностями cat
 */
@Service
public class CatService {

    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    /**
     * Сохраняет заданную сущность.
     * Используется метод репозитория {@link JpaRepository#save(Object)}
     * @param cat сохраняемая сущность
     * @return сохраняемая сущность
     */
    public Cat save(Cat cat) {
        if (cat != null) {
            return catRepository.save(cat);
        }
        return null;
    }

    /**
     * Возвращает сущность по её идентификатору.
     * Используется метод репозитория {@link JpaRepository#findById(Object)}
     * @param id идентификатор сущности
     * @return возвращаемая сущность
     */
    public Optional<Cat> findById(Integer id) {
        return catRepository.findById(id);
    }

    /**
     * Возвращает список сущностей по состоянию здоровья.
     * Используется метод репозитория {@link CatRepository#findAllByHealthy(Boolean)}
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAllByHealth(Boolean isHealthy) {
        return catRepository.findAllByHealthy(isHealthy);
    }

    /**
     * Возвращает список сущностей по наличию/отсутствию вакцинации.
     * Используется метод репозитория {@link CatRepository#findAllByVaccinated(Boolean)}
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAllByVaccinate(Boolean isVaccinated) {
        return catRepository.findAllByVaccinated(isVaccinated);
    }

    /**
     * Возвращает список всех сущностей.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    /**
     * Обновляет сущность по передаваемым параметрам.
     * Используется метод репозитория {@link CatRepository#updateById(Integer, String, Integer, Boolean, Boolean)}
     * @param id идентификатор сущности
     * @param name имя сущности
     * @param age возраст сущности
     * @param isHealthy состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return обновляемая сущность
     */
    public Cat updateBuId(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        if (id != null && name != null && age != null && isHealthy != null && isVaccinated != null) {
            return catRepository.updateById(id, name, age, isHealthy, isVaccinated);
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
            catRepository.deleteById(id);
        }
    }
}
