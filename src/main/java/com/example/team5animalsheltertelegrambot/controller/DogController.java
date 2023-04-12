package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.service.animal.DogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dog")
public class DogController {

    private final DogService dogService;

    public DogController(DogService dogService) {
        this.dogService = dogService;
    }

    @PostMapping
    public ResponseEntity<Dog> save(@RequestBody Dog dog) {
        Dog newDog = dogService.save(dog);
        if (newDog == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newDog);
    }

    @GetMapping
    public ResponseEntity<Optional<Dog>> findById(@RequestParam Integer id) {
        Optional<Dog> dog = dogService.findById(id);
        if (dog.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dog);
    }

    @GetMapping
    public ResponseEntity<List<Dog>> findByHealth(@RequestParam Boolean isHealthy) {
        List<Dog> listOfDogs = dogService.findAllByHealth(isHealthy);
        return ResponseEntity.ok(listOfDogs);
    }

    @GetMapping
    public ResponseEntity<List<Dog>> findByVaccinate(@RequestParam Boolean isVaccinated) {
        List<Dog> listOfDogs = dogService.findAllByVaccinate(isVaccinated);
        return ResponseEntity.ok(listOfDogs);
    }

    @GetMapping
    public ResponseEntity<List<Dog>> findAll() {
        List<Dog> listOfDogs = dogService.findAll();
        return ResponseEntity.ok(listOfDogs);
    }

    @PutMapping
    public ResponseEntity<Dog> updateById(@RequestParam Integer id, @RequestParam String name, @RequestParam Integer age,
                                          @RequestParam Boolean isHealthy, @RequestParam Boolean isVaccinated) {
        Dog dog = dogService.updateBuId(id, name, age, isHealthy, isVaccinated);
        if (dog == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(dog);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Integer id) {
        dogService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
