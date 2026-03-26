package com.example.chaspjava.dto;

import com.example.chaspjava.entity.Profile;

public class ProfileDTO {

    private String fullName;
    private String email;
    private Profile.Role role;
    private String phone;
    private String avatarUrl;

    public ProfileDTO() {}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile.Role getRole() {
        return role;
    }

    public void setRole(Profile.Role role) {
        this.role = role;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
