package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.entity.NamedEntity;
import com.example.team5animalsheltertelegrambot.timer.ProbationType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@DiscriminatorValue("ANIMAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@Table(name = "ANIMAL", indexes = {
        @Index(name = "IDX_ANIMAL_CAT_SHELTER", columnList = "CAT_SHELTER_ID"),
        @Index(name = "IDX_ANIMAL_DOG_SHELTER", columnList = "DOG_SHELTER_ID")
})
public class Animal extends NamedEntity {

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "IS_HEALTHY")
    private Boolean isHealthy;

    @Column(name = "IS_VACCINATED")
    private Boolean isVaccinated;

    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer adopter;

    @Column(name = "DATE_ADOPTION")
    private LocalDateTime dateAdoption;

    @Column(name = "PROBATION")
    private Integer probation;

    @OneToMany(mappedBy = "animal")
    private List<AnimalReport> animalReports;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getHealthy() {
        return isHealthy;
    }

    public void setHealthy(Boolean healthy) {
        isHealthy = healthy;
    }

    public Boolean getVaccinated() {
        return isVaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public Customer getAdopter() {
        return adopter;
    }

    public void setAdopter(Customer adopter) {
        this.adopter = adopter;
    }

    public LocalDateTime getDateAdoption() {
        return dateAdoption;
    }

    public void setDateAdoption(LocalDateTime dateAdoption) {
        this.dateAdoption = dateAdoption;
    }

    public ProbationType getProbation() {
        return probation == null ? null : ProbationType.fromId(probation);
    }

    public void setProbation(ProbationType probation) {
        this.probation = probation == null ? null : probation.getId();
    }

    public List<AnimalReport> getAnimalReports() {
        return animalReports;
    }

    public void setAnimalReports(List<AnimalReport> animalReports) {
        this.animalReports = animalReports;
    }

    @Override
    public String toString() {
        return "Имя: " + getName()
                + ", возраст: " + age
                + ", состояние здоровья: " + (isVaccinated ? "здоров(а)" : "не здоров(а)")
                + ", наличие вакцинации: " + (isHealthy ? "вакцинирован(а)" : "не вакцинирован(а)");
    }
}
