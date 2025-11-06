package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        ownerRepository.deleteAll();
    }

    @Test
    void testCreateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Luis");
        owner.setLastName("Gomez");
        owner.setAddress("Av. Siempre Viva 123");
        owner.setCity("Lima");
        owner.setTelephone("987654321");

        mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Luis"))
                .andExpect(jsonPath("$.city").value("Lima"));
    }

    @Test
    void testListOwners() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Carlos");
        owner.setLastName("Mendoza");
        owner.setAddress("Calle Los Olivos 45");
        owner.setCity("Arequipa");
        owner.setTelephone("912345678");
        ownerRepository.save(owner);

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Carlos"));
    }

    @Test
    void testUpdateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Ana");
        owner.setLastName("Lopez");
        owner.setAddress("Jr. Los Sauces 20");
        owner.setCity("Trujillo");
        owner.setTelephone("934567890");
        owner = ownerRepository.save(owner);

        owner.setCity("Cusco");

        mockMvc.perform(put("/owners/" + owner.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Cusco"));
    }

    @Test
    void testDeleteOwner() throws Exception {
        Owner owner = new Owner();
        owner.setFirstName("Pedro");
        owner.setLastName("Soto");
        owner.setAddress("Av. Los Pinos 55");
        owner.setCity("Chiclayo");
        owner.setTelephone("900111222");
        owner = ownerRepository.save(owner);

        mockMvc.perform(delete("/owners/" + owner.getId()))
                .andExpect(status().isOk());
    }
}