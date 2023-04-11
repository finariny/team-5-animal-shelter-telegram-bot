package com.example.team5animalsheltertelegrambot.entity.animal;

import com.example.team5animalsheltertelegrambot.entity.NamedEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
@MappedSuperclass
public abstract class Animal extends NamedEntity {

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
        return "Animal{" +
                "age=" + age +
                ", isHealthy=" + isHealthy +
                ", isVaccinated=" + isVaccinated +
                '}';
    }
}
