package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping
    public List<Owner> list() {
        return ownerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Owner> getById(@PathVariable Long id) {
        return ownerRepository.findById(id);
    }

    @PostMapping
    public Owner create(@RequestBody Owner owner) {
        return ownerRepository.save(owner);
    }

    @PutMapping("/{id}")
    public Owner update(@PathVariable Long id, @RequestBody Owner ownerDetails) {
        Owner owner = ownerRepository.findById(id).orElseThrow();
        owner.setFirstName(ownerDetails.getFirstName());
        owner.setLastName(ownerDetails.getLastName());
        owner.setAddress(ownerDetails.getAddress());
        owner.setCity(ownerDetails.getCity());
        owner.setTelephone(ownerDetails.getTelephone());
        return ownerRepository.save(owner);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        ownerRepository.deleteById(id);
    }
}