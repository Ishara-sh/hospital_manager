
package com.example.hospital_manager.dto;

public record AuthResponse(
    String token,
    String type,
    long expiresIn,
    String role
) {}
