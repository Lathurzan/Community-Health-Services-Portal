package com.example.chaspjava.service;

import com.example.chaspjava.dto.ProfileDTO;
import com.example.chaspjava.entity.Profile;

import java.util.List;

public interface ProfileService {
    Profile create(ProfileDTO dto);
    Profile getById(String id);
    List<Profile> getAll();
    long count();

    long countByRole(Profile.Role role);

    default long countProviders() { return countByRole(Profile.Role.provider); }

    default long countPatients() { return countByRole(Profile.Role.patient); }

    /** Change the password for a user. Validates oldPassword if provided. */
    void changePassword(String userId, String oldPassword, String newPassword);
}
