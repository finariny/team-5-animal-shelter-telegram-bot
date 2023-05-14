package com.example.team5animalsheltertelegrambot.service.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.repository.animal.CatRepository;
import com.example.team5animalsheltertelegrambot.timer.ProbationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с кошками
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
     *
     * @param cat сохраняемая сущность
     * @return {@code true} - сущность сохранена, {@code false} - сущность не сохранена
     */
    public boolean save(Cat cat) {
        if (!(cat == null
                || (cat.getName() == null || cat.getName().isBlank())
                || (cat.getAge() == null || cat.getAge() < 0)
                || cat.getHealthy() == null
                || cat.getVaccinated() == null)) {
            catRepository.save(cat);
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
    public Optional<Cat> findById(int id) {
        return catRepository.findById(id);
    }

    /**
     * Возвращает список сущностей по значению состояния здоровья.
     * Используется метод репозитория {@link CatRepository#findAllByHealth(Boolean)}
     *
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAllByHealth(Boolean isHealthy) {
        return catRepository.findAllByHealth(isHealthy);
    }

    /**
     * Возвращает список сущностей по значению наличия/отсутствия вакцинации.
     * Используется метод репозитория {@link CatRepository#findAllByVaccination(Boolean)}
     *
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAllByVaccinate(Boolean isVaccinated) {
        return catRepository.findAllByVaccination(isVaccinated);
    }

    /**
     * Возвращает список сущностей по значению состояния здоровья и/или значению наличия/отсутствия вакцинации.
     * Используется метод репозитория {@link CatRepository#findAllByHealthAndVaccination(Boolean, Boolean)}
     *
     * @param isHealthy    состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAllByHealthAndVaccination(Boolean isHealthy, Boolean isVaccinated) {
        return catRepository.findAllByHealthAndVaccination(isHealthy, isVaccinated);
    }

    /**
     * Возвращает список всех сущностей.
     * Используется метод репозитория {@link JpaRepository#findAll()}
     *
     * @return список возвращаемых сущностей
     */
    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    /**
     * Обновляет сущность по передаваемым параметрам.
     * Используется метод репозитория {@link CatRepository#updateById(Integer, String, Integer, Boolean, Boolean)}
     *
     * @param id           идентификатор сущности
     * @param name         имя сущности
     * @param age          возраст сущности
     * @param isHealthy    состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return число ({@code 1} - сущность обновлена, {@code 0} - сущность не обновлена)
     */
    public Integer updateById(Integer id, String name, Integer age, Boolean isHealthy, Boolean isVaccinated) {
        Optional<Cat> cat = findById(id);
        if (cat.isEmpty()
                || (name == null || name.isBlank())
                || (age == null || age < 0)
                || isHealthy == null
                || isVaccinated == null) {
            return 0;
        } else {
            catRepository.updateById(id, name, age, isHealthy, isVaccinated);
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
    public Boolean deleteById(int id) {
        Optional<Cat> findCatById = findById(id);
        if (findCatById.isEmpty()) {
            return false;
        }
        catRepository.deleteById(id);
        return true;
    }

    /**
     * Регистрирует усыновление кошки посетителем, добавляет текущую дату регистрации
     *
     * @param cat           кошка
     * @param customer      посетитель
     * @param probationType испытательный срок
     * @return {@code true} - сущность обновлена, {@code false} - сущность не обновлена
     */
    public boolean adopt(@NotNull Cat cat, @NotNull Customer customer, ProbationType probationType) {
        cat.setAdopter(customer);
        cat.setDateAdoption(LocalDateTime.now());
        cat.setProbation(probationType);
        return this.save(cat);
    }

    /**
     * Находит всех кошек на испытательном сроке (которых усыновили)
     *
     * @param probationTypes Массив типов испытательного срока
     * @return Список всех кошек на испытательном сроке (которых усыновили)
     */
    public List<Cat> findOnProbation(ProbationType... probationTypes) {
        List<Integer> integers = new ArrayList<>();
        for (ProbationType probation : probationTypes) {
            integers.add(probation.getId());
        }
        return catRepository.findByProbationIn(integers);
    }
}
