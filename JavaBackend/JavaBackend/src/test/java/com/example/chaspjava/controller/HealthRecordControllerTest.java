package com.example.chaspjava.controller;

import com.example.chaspjava.dto.HealthRecordDTO;
import com.example.chaspjava.entity.HealthRecord;
import com.example.chaspjava.service.HealthRecordService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
class HealthRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HealthRecordService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createHealthRecord_returnsOk() throws Exception {
        HealthRecordDTO dto = new HealthRecordDTO();
        when(service.create(any(HealthRecordDTO.class))).thenReturn(new HealthRecord());

        mockMvc.perform(post("/api/health-records/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void getByPatient_returnsList() throws Exception {
        HealthRecord hr = new HealthRecord();
        hr.setId("hr-1");

        when(service.getByPatient("patient-1")).thenReturn(java.util.List.of(hr));

        mockMvc.perform(get("/api/health-records/patient/patient-1"))
                .andExpect(status().isOk());
    }

    @Test
    void getByProvider_returnsList() throws Exception {
        HealthRecord hr = new HealthRecord();
        hr.setId("hr-1");

        when(service.getByProvider("provider-1")).thenReturn(java.util.List.of(hr));

        mockMvc.perform(get("/api/health-records/provider/provider-1"))
                .andExpect(status().isOk());
    }

    @Test
    void getById_returnsRecord() throws Exception {
        HealthRecord hr = new HealthRecord();
        hr.setId("hr-1");

        when(service.getById("hr-1")).thenReturn(hr);

        mockMvc.perform(get("/api/health-records/hr-1"))
                .andExpect(status().isOk());
    }

    @Test
    void countGoodPatients_withValidParam_returnsCount() throws Exception {
        when(service.countGoodPatients(80)).thenReturn(3L);

        mockMvc.perform(get("/api/health-records/good-patients/count").param("minScore", "80"))
                .andExpect(status().isOk());
    }

    @Test
    void countGoodPatients_invalidParam_throwsUnprocessableEntity() throws Exception {
        mockMvc.perform(get("/api/health-records/good-patients/count").param("minScore", "200"))
                .andExpect(status().isUnprocessableEntity());
    }
}
