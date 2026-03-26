package com.example.chaspjava.controller;

import com.example.chaspjava.dto.PatientHealthMetricDTO;
import com.example.chaspjava.entity.PatientHealthMetric;
import com.example.chaspjava.service.PatientHealthMetricService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientHealthMetricController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatientHealthMetricControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientHealthMetricService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createMetric_returnsOk() throws Exception {
        PatientHealthMetricDTO dto = new PatientHealthMetricDTO();
        when(service.create(any(PatientHealthMetricDTO.class))).thenReturn(new PatientHealthMetric());

        mockMvc.perform(post("/api/health-metrics/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getByPatient_returnsList() throws Exception {
        PatientHealthMetric m = new PatientHealthMetric();
        m.setId("phm-1");

        when(service.getByPatient("patient-1")).thenReturn(java.util.List.of(m));

        mockMvc.perform(get("/api/health-metrics/patient/patient-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("phm-1"));
    }
}
