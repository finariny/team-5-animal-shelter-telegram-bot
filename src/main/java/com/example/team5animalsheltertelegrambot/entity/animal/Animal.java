package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;

import javax.persistence.*;

@Entity
@Table(name = "animal")
@DiscriminatorValue(value = "ANIMAL")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class Animal extends NamedEntity {

    @Column(name = "age")
    private Integer age;

    @Column(name = "is_healthy")
    private Boolean isHealthy;

    @Column(name = "is_vaccinated")
    private Boolean isVaccinated;

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

    @Override
    public String toString() {
        return "Имя: " + getName()
                + ", возраст: " + age
                + ", состояние здоровья: " + (isVaccinated ? "здоров(а)" : "не здоров(а)")
                + ", наличие вакцинации: " + (isHealthy ? "вакцинирован(а)" : "не вакцинирован(а)");
    }
}
