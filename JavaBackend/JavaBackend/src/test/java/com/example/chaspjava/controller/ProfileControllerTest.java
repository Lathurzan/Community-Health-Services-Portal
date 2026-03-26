package com.example.chaspjava.controller;

import com.example.chaspjava.dto.ProfileDTO;
import com.example.chaspjava.entity.Profile;
import com.example.chaspjava.service.ProfileService;
import com.example.chaspjava.dto.ChangePasswordDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createProfile_returnsCreatedProfile() throws Exception {
        ProfileDTO dto = new ProfileDTO();
        dto.setFullName("Jane Doe");
        dto.setEmail("jane@example.com");
        dto.setRole(Profile.Role.patient);
        dto.setPhone("+123456789");
        dto.setAvatarUrl("http://example.com/avatar.png");

        Profile profile = new Profile();
        profile.setId("id-123");
        profile.setFullName(dto.getFullName());
        profile.setEmail(dto.getEmail());
        profile.setRole(dto.getRole());
        profile.setPhone(dto.getPhone());
        profile.setAvatarUrl(dto.getAvatarUrl());

        when(service.create(any(ProfileDTO.class))).thenReturn(profile);

        mockMvc.perform(post("/api/profiles/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(profile.getId()))
                .andExpect(jsonPath("$.email").value(profile.getEmail()))
                .andExpect(jsonPath("$.fullName").value(profile.getFullName()));

        verify(service).create(any(ProfileDTO.class));
    }

    @Test
    void getProfileById_returnsProfile() throws Exception {
        String id = "id-456";
        Profile profile = new Profile();
        profile.setId(id);
        profile.setEmail("bob@example.com");
        profile.setFullName("Bob Smith");
        profile.setRole(Profile.Role.provider);

        when(service.getById(id)).thenReturn(profile);

        mockMvc.perform(get("/api/profiles/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.email").value(profile.getEmail()))
                .andExpect(jsonPath("$.fullName").value(profile.getFullName()));

        verify(service).getById(id);
    }

    @Test
    void getAll_returnsListOfProfiles() throws Exception {
        Profile p = new Profile();
        p.setId("p-1");
        p.setEmail("a@ex.com");
        p.setFullName("A Example");

        when(service.getAll()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/profiles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(p.getId()));

        verify(service).getAll();
    }

    @Test
    void changePassword_success_returnsOk() throws Exception {
    ChangePasswordDTO dto = new ChangePasswordDTO();
    dto.setUserId("user-1");
    dto.setOldPassword("old");
    dto.setNewPassword("new");

    // service.changePassword returns void
    mockMvc.perform(post("/api/profiles/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk());

    verify(service).changePassword("user-1", "old", "new");
    }

    @Test
    void changePassword_missingUserId_throwsUnprocessable() throws Exception {
    ChangePasswordDTO dto = new ChangePasswordDTO();
    // missing userId triggers IllegalArgumentException in controller

    mockMvc.perform(post("/api/profiles/change-password")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void countEndpoints_returnValues() throws Exception {
    when(service.count()).thenReturn(10L);
    when(service.countProviders()).thenReturn(4L);
    when(service.countByRole(Profile.Role.patient)).thenReturn(6L);

    mockMvc.perform(get("/api/profiles/count")).andExpect(status().isOk())
        .andExpect(jsonPath("$").value(10));

    mockMvc.perform(get("/api/profiles/count/providers")).andExpect(status().isOk())
        .andExpect(jsonPath("$").value(4));

    mockMvc.perform(get("/api/profiles/count/patients")).andExpect(status().isOk())
        .andExpect(jsonPath("$").value(6));
    }

    @Test
    void countPatients_serviceThrows_returnsServerError() throws Exception {
    when(service.countByRole(Profile.Role.patient)).thenThrow(new RuntimeException("boom"));

    mockMvc.perform(get("/api/profiles/count/patients"))
        .andExpect(status().isInternalServerError());
    }
}
