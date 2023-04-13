package com.example.team5animalsheltertelegrambot.repository.animal;

import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с сущностями dog
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    /**
     * Обновляет сущность по передаваемым параметрам
     * @param id идентификатор сущности, не должен быть {@code null}
     * @param name имя сущности, не должен быть {@code null}
     * @param age возраст сущности, не должен быть {@code null}
     * @param isHealthy состояние здоровья сущности ({@code true} - здоров, {@code false} - не здоров), не должен быть {@code null}
     * @param isVaccinated наличие вакцинации сущности ({@code true} - вакцинирован, {@code false} - не вакцинирован), не должен быть {@code null}
     * @return обновленная сущность
     */
    @Modifying
    @Query(value =
            "UPDATE dog SET name = %:name%, age = %:age%, is_healthy = %:isHealthy%, is_vaccinated = %:isVaccinated% WHERE id = %:id%;",
            nativeQuery = true)
    Dog updateById(@Param("id") Integer id, @Param("name") String name, @Param("age") Integer age,
                   @Param("isHealthy") Boolean isHealthy, @Param("isVaccinated") Boolean isVaccinated);

    /**
     * Возвращает список сущностей по состоянию здоровья
     * @param isHealthy состояние здоровья ({@code true} - здоров, {@code false} - не здоров), не должен быть {@code null}
     * @return список возвращенных сущностей
     */
    List<Dog> findAllByHealthy(Boolean isHealthy);

    /**
     * Возвращает список сущностей по наличию/отсутствию вакцинации
     * @param isVaccinated наличие вакцинации ({@code true} - вакцинирован, {@code false} - не вакцинирован), не должен быть {@code null}
     * @return список возвращенных сущностей
     */
    List<Dog> findAllByVaccinated(Boolean isVaccinated);
}
