package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.repositories.VetRepository;
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
public class VetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VetRepository vetRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        vetRepository.deleteAll();
    }

    @Test
    void testCreateVet() throws Exception {
        Vet vet = new Vet();
        vet.setFirstName("Juan");
        vet.setLastName("Perez");

        mockMvc.perform(post("/vets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"))
                .andExpect(jsonPath("$.lastName").value("Perez"));
    }

    @Test
    void testListVets() throws Exception {
        Vet vet1 = new Vet();
        vet1.setFirstName("Maria");
        vet1.setLastName("Lopez");
        vetRepository.save(vet1);

        mockMvc.perform(get("/vets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("Maria"));
    }

    @Test
    void testUpdateVet() throws Exception {
        Vet vet = new Vet();
        vet.setFirstName("Carlos");
        vet.setLastName("Sanchez");
        vet = vetRepository.save(vet);

        vet.setLastName("Ramirez");

        mockMvc.perform(put("/vets/" + vet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Ramirez"));
    }

    @Test
    void testDeleteVet() throws Exception {
        Vet vet = new Vet();
        vet.setFirstName("Ana");
        vet.setLastName("Torres");
        vet = vetRepository.save(vet);

        mockMvc.perform(delete("/vets/" + vet.getId()))
                .andExpect(status().isOk());
    }
}
