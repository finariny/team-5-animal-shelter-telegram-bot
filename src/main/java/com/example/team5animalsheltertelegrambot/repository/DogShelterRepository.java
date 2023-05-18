package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.shelter.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий приюта для собак
 */
@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Integer> {
}