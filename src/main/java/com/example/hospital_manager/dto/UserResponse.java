
package com.example.hospital_manager.dto;

public record UserResponse(
    Long id,
    String username,
    String fullName,
    String email,
    String role
) {}
