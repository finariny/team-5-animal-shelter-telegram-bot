package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с животными
 */
@Repository
public interface AnimalRepository<T extends Animal> extends JpaRepository<T, Integer> {

    /**
     * Обновляет сущность по передаваемым параметрам
     *
     * @param id           идентификатор сущности, не должен быть {@code null}
     * @param name         имя сущности, не должно быть {@code null}
     * @param age          возраст сущности, не должен быть {@code null}
     * @param isHealthy    состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров), не должно быть {@code null}
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован), не должно быть {@code null}
     * @return число ({@code 1} - сущность обновлена, {@code 0} - сущность не обновлена)
     */
    @Modifying
    @Query(value = "UPDATE Animal SET name = :name, age = :age, isHealthy = :isHealthy, isVaccinated = :isVaccinated WHERE id = :id")
    Integer updateById(@Param("id") Integer id,
                       @Param("name") String name,
                       @Param("age") Integer age,
                       @Param("isHealthy") Boolean isHealthy,
                       @Param("isVaccinated") Boolean isVaccinated);

    /**
     * Возвращает список сущностей по значению состояния здоровья
     *
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров), не должно быть {@code null}
     * @return список возвращенных сущностей
     */
    @Query(value = "SELECT a FROM Animal a WHERE a.isHealthy = :isHealthy")
    List<T> findAllByHealth(@Param("isHealthy") Boolean isHealthy);

    /**
     * Возвращает список сущностей по значению наличия/отсутствия вакцинации
     *
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован), не должно быть {@code null}
     * @return список возвращенных сущностей
     */
    @Query(value = "SELECT a FROM Animal a WHERE a.isVaccinated = :isVaccinated")
    List<T> findAllByVaccination(@Param("isVaccinated") Boolean isVaccinated);

    /**
     * Возвращает список сущностей по значению состояния здоровья и/или значению наличия/отсутствия вакцинации
     *
     * @param isHealthy    состояние здоровья ({@code true} - здоров, {@code false} - не здоров)
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован)
     * @return список возвращаемых сущностей
     */
    @Query(value = "SELECT a FROM Animal a WHERE a.isHealthy = :isHealthy AND a.isVaccinated = :isVaccinated")
    List<T> findAllByHealthAndVaccination(@Param("isHealthy") Boolean isHealthy, @Param("isVaccinated") Boolean isVaccinated);
}
