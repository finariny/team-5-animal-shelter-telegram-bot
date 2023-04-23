package com.example.team5animalsheltertelegrambot.entity.shelter;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.person.Employee;
import com.example.team5animalsheltertelegrambot.exceptions.ValidationException;
import com.example.team5animalsheltertelegrambot.service.ValidationRegularService;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This is superClass for Cat and Dog Shelter
 */


@Getter

@MappedSuperclass
public abstract class AnimalShelter extends NamedEntity {

    @Column(name = "address")
    private String address;

    @Column(name = "work_schedule")
    private String workSchedule;

    @Column(name = "driving_directions")
    private String drivingDirections;

    @Column(name = "contacts")
    private String contacts; //строка обязательно должна содержать номер телефона

    @Column(name = "safety_advice")
    private String safetyAdvice;

    @Column(name = "description")
    private String description;

    @OneToMany
    private List<Customer> customers;

    @OneToMany
    private List<Employee> employees;

    @Override
    public String toString() {
        return getName();
    }

    public void setAddress(String address) {
        if(!ValidationRegularService.validateBaseStr(address)){
            throw new ValidationException(address);
        }
        this.address = address;
    }

    public void setWorkSchedule(String workSchedule) {
        if(!ValidationRegularService.validateBaseStr(workSchedule)){
            throw new ValidationException(workSchedule);
        }
        this.workSchedule = workSchedule;
    }

    public void setDrivingDirections(String drivingDirections) {
        if(!ValidationRegularService.validateBaseStr(drivingDirections)){
            throw new ValidationException(drivingDirections);
        }
        this.drivingDirections = drivingDirections;
    }

    public void setContacts(String contacts) {
        if(!ValidationRegularService.findValidatePhone(contacts)){
            throw new ValidationException("Не обнаружен номер телефона в контактах");
        }
        this.contacts = contacts;
    }

    public void setSafetyAdvice(String safetyAdvice) {
        if(!ValidationRegularService.validateBaseStr(safetyAdvice)){
            throw new ValidationException(safetyAdvice);
        }
        this.safetyAdvice = safetyAdvice;
    }

    public void setDescription(String description) {
        if(!ValidationRegularService.validateBaseStr(description)){
            throw new ValidationException(description);
        }
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AnimalShelter that = (AnimalShelter) o;
        return Objects.equals(address, that.address) && Objects.equals(workSchedule, that.workSchedule) && Objects.equals(drivingDirections, that.drivingDirections) && Objects.equals(contacts, that.contacts) && Objects.equals(safetyAdvice, that.safetyAdvice) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, workSchedule, drivingDirections, contacts, safetyAdvice, description);
    }
}
