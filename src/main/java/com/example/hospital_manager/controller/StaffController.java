

package com.example.hospital_manager.controller;

import com.example.hospital_manager.dto.UserResponse;
import com.example.hospital_manager.model.*;
import com.example.hospital_manager.service.AuthService;
import com.example.hospital_manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaffController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        return authService.getUsers();
    }

    @GetMapping("/doctors")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public List<UserResponse> getDoctors() {
        return authService.getDoctors();
    }

    @PostMapping("/admin/doctors")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addDoctor(@RequestBody AppUser doctor) {

        doctor.setId(null);
        doctor.setRole(Role.DOCTOR);

        if (userRepository.existsByUsername(doctor.getUsername())) {
            throw new com.example.hospitalmanager.exception.ApiException(
                    org.springframework.http.HttpStatus.CONFLICT,
                    "Username already exists");
        }

        throw new com.example.hospitalmanager.exception.ApiException(
                org.springframework.http.HttpStatus.BAD_REQUEST,
                "Create doctor accounts through the registration endpoint");
    }
}
