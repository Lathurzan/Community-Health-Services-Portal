package com.example.chaspjava.controller;

import com.example.chaspjava.dto.ProfileDTO;
import com.example.chaspjava.entity.Profile;
import com.example.chaspjava.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public Profile create(@RequestBody ProfileDTO dto) {
        return service.create(dto);
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody com.example.chaspjava.dto.ChangePasswordDTO dto) {
        if (dto == null || dto.getUserId() == null) throw new IllegalArgumentException("userId is required");
        service.changePassword(dto.getUserId(), dto.getOldPassword(), dto.getNewPassword());
    }

    @GetMapping("/{id}")
    public Profile get(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Profile> all() {
        return service.getAll();
    }

    @GetMapping("/count")
    public long count() {
        return service.count();
    }

    @GetMapping("/count/providers")
    public long countProviders() {
        return service.countProviders();
    }

    @GetMapping("/count/patients")
    public long countPatients() {
        try {
            return service.countByRole(Profile.Role.patient);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to count patients", ex);
        }
    }
}
