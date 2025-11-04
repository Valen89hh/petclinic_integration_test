package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vets")
public class VetController {

    @Autowired
    private VetRepository vetRepository;

    @GetMapping
    public List<Vet> list() {
        return vetRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Vet> getById(@PathVariable Integer id) {
        return vetRepository.findById(id);
    }

    @PostMapping
    public Vet create(@RequestBody Vet vet) {
        return vetRepository.save(vet);
    }

    @PutMapping("/{id}")
    public Vet update(@PathVariable Integer id, @RequestBody Vet vetDetails) {
        Vet vet = vetRepository.findById(id).orElseThrow();
        vet.setFirstName(vetDetails.getFirstName());
        vet.setLastName(vetDetails.getLastName());
        return vetRepository.save(vet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        vetRepository.deleteById(id);
    }
}