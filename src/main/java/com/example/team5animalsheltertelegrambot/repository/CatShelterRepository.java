package com.example.team5animalsheltertelegrambot.repository;


import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.shelter.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Репозиторий Приюта кошек */
@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Integer> {


}