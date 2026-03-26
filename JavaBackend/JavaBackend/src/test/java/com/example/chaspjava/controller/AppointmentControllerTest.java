package com.example.chaspjava.controller;

import com.example.chaspjava.dto.AppointmentDTO;
import com.example.chaspjava.entity.Appointment;
import com.example.chaspjava.service.AppointmentService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppointmentController.class)
// Loads only the AppointmentController for isolated controller testing
@AutoConfigureMockMvc(addFilters = false)
// Disables Spring Security filters to avoid 403 responses during tests
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    // MockMvc simulates HTTP requests without starting the server
    @MockBean
    private AppointmentService service;
    // Service layer is mocked to isolate controller behaviour

    @Autowired
    private ObjectMapper objectMapper;
    // Converts Java objects to JSON and vice versa

    @Test
    void createAppointment_returnsAppointment() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();
        // minimal dto values may vary

        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.create(any(AppointmentDTO.class))).thenReturn(a);

        mockMvc.perform(post("/api/appointments/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("appt-1"));
    }

    @Test
    void getById_returnsAppointment() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getById("appt-1")).thenReturn(a);

        mockMvc.perform(get("/api/appointments/appt-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("appt-1"));
    }

    @Test
    void getByPatient_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getByPatient("patient-1")).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments/patient/patient-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void getByProvider_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getByProvider("provider-1")).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments/provider/provider-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void getAll_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getAll()).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void count_returnsLong() throws Exception {
        when(service.count()).thenReturn(5L);

        mockMvc.perform(get("/api/appointments/count"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    void getToday_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getTodayAppointments()).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments/today"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void getTomorrow_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getTomorrowAppointments()).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments/tomorrow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void getUpcoming_withDaysParam_returnsList() throws Exception {
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.getUpcomingAppointments(3)).thenReturn(java.util.List.of(a));

        mockMvc.perform(get("/api/appointments/upcoming").param("days", "3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("appt-1"));
    }

    @Test
    void delete_returnsNoContent_onSuccess() throws Exception {
        doNothing().when(service).delete("appt-1");

        mockMvc.perform(delete("/api/appointments/delete/appt-1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_returnsNotFound_onMissing() throws Exception {
        doThrow(new RuntimeException("not found")).when(service).delete("missing");

        mockMvc.perform(delete("/api/appointments/delete/missing"))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_returnsUpdatedAppointment() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();
        Appointment a = new Appointment();
        a.setId("appt-1");

        when(service.update(any(String.class), any(AppointmentDTO.class))).thenReturn(a);

        mockMvc.perform(put("/api/appointments/update/appt-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("appt-1"));
    }

    @Test
    void update_returnsNotFound_whenMissing() throws Exception {
        AppointmentDTO dto = new AppointmentDTO();

        when(service.update(any(String.class), any(AppointmentDTO.class))).thenThrow(new RuntimeException("not found"));

        mockMvc.perform(put("/api/appointments/update/missing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}
