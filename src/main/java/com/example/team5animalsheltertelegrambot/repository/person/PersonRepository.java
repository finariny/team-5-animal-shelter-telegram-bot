package com.example.team5animalsheltertelegrambot.repository.person;

import com.example.team5animalsheltertelegrambot.entity.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для Person
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
