
package com.example.hospital_manager.controller;

import com.example.hospital_manager.dto.RegisterRequest;
import com.example.hospital_manager.dto.UserResponse;
import com.example.hospital_manager.model.Role;
import com.example.hospital_manager.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaffController {

    private final AuthService authService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return authService.getUsers();
    }

    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addStaff(
            @Valid @RequestBody RegisterRequest request) {

        return authService.registerStaff(request);
    }

    @GetMapping("/doctors")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public List<UserResponse> getDoctors() {
        return authService.getDoctors();
    }

    @PostMapping("/admin/doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addDoctor(
            @Valid @RequestBody RegisterRequest request) {

        RegisterRequest doctorRequest = new RegisterRequest(
                request.username(),
                request.password(),
                request.fullName(),
                request.email(),
                Role.DOCTOR
        );

        return authService.registerStaff(doctorRequest);
    }
}
