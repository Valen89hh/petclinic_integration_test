package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @GetMapping
    public List<Specialty> list() {
        return specialtyRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Specialty> getById(@PathVariable Integer id) {
        return specialtyRepository.findById(id);
    }

    @PostMapping
    public Specialty create(@RequestBody Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @PutMapping("/{id}")
    public Specialty update(@PathVariable Integer id, @RequestBody Specialty specialtyDetails) {
        Specialty specialty = specialtyRepository.findById(id).orElseThrow();
        specialty.setName(specialtyDetails.getName());
        return specialtyRepository.save(specialty);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        specialtyRepository.deleteById(id);
    }
}