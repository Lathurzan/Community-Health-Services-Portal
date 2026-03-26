package com.example.chaspjava.controller;

import com.example.chaspjava.dto.MedicationDTO;
import com.example.chaspjava.entity.Medication;
import com.example.chaspjava.service.MedicationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicationController.class)
@AutoConfigureMockMvc(addFilters = false)
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMedication_returnsOk() throws Exception {
        MedicationDTO dto = new MedicationDTO();
        when(service.create(any(MedicationDTO.class))).thenReturn(new Medication());

        mockMvc.perform(post("/api/medications/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getByPatient_returnsList() throws Exception {
        Medication m = new Medication();
        m.setId("med-1");

        when(service.getByPatient("patient-1")).thenReturn(java.util.List.of(m));

        mockMvc.perform(get("/api/medications/patient/patient-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("med-1"));
    }

    @Test
    void getByProvider_returnsList() throws Exception {
        Medication m = new Medication();
        m.setId("med-1");

        when(service.getByProvider("provider-1")).thenReturn(java.util.List.of(m));

        mockMvc.perform(get("/api/medications/provider/provider-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("med-1"));
    }

    @Test
    void deactivate_returnsMedication() throws Exception {
        Medication m = new Medication();
        m.setId("med-1");

        when(service.markInactive("med-1")).thenReturn(m);

        mockMvc.perform(put("/api/medications/deactivate/med-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("med-1"));
    }

    @Test
    void getById_returnsMedication() throws Exception {
        Medication m = new Medication();
        m.setId("med-1");

        when(service.getById("med-1")).thenReturn(m);

        mockMvc.perform(get("/api/medications/med-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("med-1"));
    }
}
