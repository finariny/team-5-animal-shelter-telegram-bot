package com.example.team5animalsheltertelegrambot.controller;

import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.service.animal.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cat")
public class CatController {

    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PostMapping
    public ResponseEntity<Cat> save(@RequestBody Cat cat) {
        Cat newCat = catService.save(cat);
        if (newCat == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newCat);
    }

    @GetMapping
    public ResponseEntity<Optional<Cat>> findById(@RequestParam Integer id) {
        Optional<Cat> cat = catService.findById(id);
        if (cat.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cat);
    }

    @GetMapping
    public ResponseEntity<List<Cat>> findByHealth(@RequestParam Boolean isHealthy) {
        List<Cat> listOfCats = catService.findAllByHealth(isHealthy);
        return ResponseEntity.ok(listOfCats);
    }

    @GetMapping
    public ResponseEntity<List<Cat>> findByVaccinate(@RequestParam Boolean isVaccinated) {
        List<Cat> listOfCats = catService.findAllByVaccinate(isVaccinated);
        return ResponseEntity.ok(listOfCats);
    }

    @GetMapping
    public ResponseEntity<List<Cat>> findAll() {
        List<Cat> listOfCats = catService.findAll();
        return ResponseEntity.ok(listOfCats);
    }

    @PutMapping
    public ResponseEntity<Cat> updateById(@RequestParam Integer id, @RequestParam String name, @RequestParam Integer age,
                                          @RequestParam Boolean isHealthy, @RequestParam Boolean isVaccinated) {
        Cat cat = catService.updateBuId(id, name, age, isHealthy, isVaccinated);
        if (cat == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cat);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Integer id) {
        catService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
