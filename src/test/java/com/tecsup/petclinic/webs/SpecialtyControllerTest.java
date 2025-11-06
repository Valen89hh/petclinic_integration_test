package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
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
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        specialtyRepository.deleteAll();
    }

    @Test
    void testCreateSpecialty() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setName("Dentistry");

        mockMvc.perform(post("/specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Dentistry"));
    }

    @Test
    void testListSpecialties() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setName("Surgery");
        specialtyRepository.save(specialty);

        mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Surgery"));
    }

    @Test
    void testUpdateSpecialty() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setName("Cardiology");
        specialty = specialtyRepository.save(specialty);

        specialty.setName("Neurology");

        mockMvc.perform(put("/specialties/" + specialty.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(specialty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Neurology"));
    }

    @Test
    void testDeleteSpecialty() throws Exception {
        Specialty specialty = new Specialty();
        specialty.setName("Dermatology");
        specialty = specialtyRepository.save(specialty);

        mockMvc.perform(delete("/specialties/" + specialty.getId()))
                .andExpect(status().isOk());
    }
}
