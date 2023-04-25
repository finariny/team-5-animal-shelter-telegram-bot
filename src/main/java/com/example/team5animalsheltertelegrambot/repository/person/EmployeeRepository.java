package com.example.team5animalsheltertelegrambot.repository.person;

import com.example.team5animalsheltertelegrambot.entity.person.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для сотрудника / волонтера
 */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
