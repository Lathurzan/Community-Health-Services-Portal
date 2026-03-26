package com.example.chaspjava.repository;

import com.example.chaspjava.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, String> {

    Optional<Profile> findByEmail(String email);

    long countByRole(Profile.Role role);
}
