package com.example.team5animalsheltertelegrambot.repository;

import com.example.team5animalsheltertelegrambot.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
