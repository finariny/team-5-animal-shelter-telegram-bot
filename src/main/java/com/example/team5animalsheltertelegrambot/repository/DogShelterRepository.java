package com.example.team5animalsheltertelegrambot.repository;



import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Репозиторий Приюта собак */
@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Integer> {

}