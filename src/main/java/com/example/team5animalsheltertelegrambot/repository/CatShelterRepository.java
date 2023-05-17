package com.example.team5animalsheltertelegrambot.repository;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий Приюта кошек */
@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Integer> {


}