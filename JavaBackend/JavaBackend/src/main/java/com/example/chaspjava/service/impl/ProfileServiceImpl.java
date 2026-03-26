package com.example.chaspjava.service.impl;

import com.example.chaspjava.dto.ProfileDTO;
import com.example.chaspjava.entity.Profile;
import com.example.chaspjava.repository.ProfileRepository;
import com.example.chaspjava.service.ProfileService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repo;

    public ProfileServiceImpl(ProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public Profile create(ProfileDTO dto) {
        Profile p = new Profile();
        p.setId(UUID.randomUUID().toString());
        p.setFullName(dto.getFullName());
        p.setEmail(dto.getEmail());
        p.setAvatarUrl(dto.getAvatarUrl());
        p.setPhone(dto.getPhone());
        p.setRole(dto.getRole());
        return repo.save(p);
    }

    @Override
    public Profile getById(String id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
    }

    @Override
    public List<Profile> getAll() {
        return repo.findAll();
    }

    @Override
    public long count() {
        return repo.count();
    }

    @Override
    public long countByRole(Profile.Role role) {
        return repo.countByRole(role);
    }

    @Override
    public void changePassword(String userId, String oldPassword, String newPassword) {
        Profile p = repo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("newPassword must be at least 6 characters");
        }

        // If an old password is supplied, validate it. If none exists in record, skip.
        if (oldPassword != null && p.getPassword() != null) {
            if (!oldPassword.equals(p.getPassword())) {
                throw new IllegalArgumentException("oldPassword does not match");
            }
        }

        p.setPassword(newPassword);
        repo.save(p);
    }
}
