package com.example.team5animalsheltertelegrambot.entity.shelter;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.person.Employee;
import com.example.team5animalsheltertelegrambot.exception.ValidationException;
import com.example.team5animalsheltertelegrambot.service.ValidationRegularService;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * This is superClass for Cat and Dog Shelter
 */
@Entity
@DiscriminatorValue("ASH")
@Table(name = "ANIMAL_SHELTER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class AnimalShelter extends NamedEntity {

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "WORK_SCHEDULE")
    private String workSchedule;

    @Column(name = "DRIVING_DIRECTIONS")
    private String drivingDirections;

    @Column(name = "CONTACTS")
    private String contacts; //строка обязательно должна содержать номер телефона

    @Column(name = "SAFETY_ADVICE")
    private String safetyAdvice;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "IMAGE_NAME")
    private String imageName;

    @Column(name = "SECURITY_CONTACT")
    private String securityContact;

    @Column(name = "SHELTER_SAFETY_ADVICE")
    private String shelterSafetyAdvice;


    @JoinTable(name = "ANIMAL_SHELTER_CUSTOMER_LINK",
            joinColumns = @JoinColumn(name = "ANIMAL_SHELTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    @ManyToMany
    private List<Customer> customers;

    @JoinTable(name = "ANIMAL_SHELTER_EMPLOYEE_LINK",
            joinColumns = @JoinColumn(name = "ANIMAL_SHELTER_ID"),
            inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID"))
    @ManyToMany
    private List<Employee> employees;

    public AnimalShelter() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (!ValidationRegularService.validateBaseStr(address)) {
            throw new ValidationException(address);
        }
        this.address = address;
    }

    public String getWorkSchedule() {
        return workSchedule;
    }

    public void setWorkSchedule(String workSchedule) {
        if (!ValidationRegularService.validateBaseStr(workSchedule)) {
            throw new ValidationException(workSchedule);
        }
        this.workSchedule = workSchedule;
    }

    public String getDrivingDirections() {
        return drivingDirections;
    }

    public void setDrivingDirections(String drivingDirections) {
        if (!ValidationRegularService.validateBaseStr(drivingDirections)) {
            throw new ValidationException(drivingDirections);
        }
        this.drivingDirections = drivingDirections;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        if (!ValidationRegularService.findValidatePhone(contacts)) {
            throw new ValidationException("Не обнаружен номер телефона в контактах");
        }
        this.contacts = contacts;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getSafetyAdvice() {
        return safetyAdvice;
    }

    public void setSafetyAdvice(String safetyAdvice) {
        if (!ValidationRegularService.validateBaseStr(safetyAdvice)) {
            throw new ValidationException(safetyAdvice);
        }
        this.safetyAdvice = safetyAdvice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (!ValidationRegularService.validateBaseStr(description)) {
            throw new ValidationException(description);
        }
        this.description = description;
    }

    public String getSecurityContact() {
        return securityContact;
    }

    public void setSecurityContact(String securityContact) {
        this.securityContact = securityContact;
    }

    public String getShelterSafetyAdvice() {
        return shelterSafetyAdvice;
    }

    public void setShelterSafetyAdvice(String shelterSafetyAdvice) {
        this.shelterSafetyAdvice = shelterSafetyAdvice;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
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

    @Override
    public String toString() {
        return getName();
    }
}
