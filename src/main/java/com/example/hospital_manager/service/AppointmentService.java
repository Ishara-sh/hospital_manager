

package com.example.hospital_manager.service;

import com.example.hospital_manager.dto.*;
import com.example.hospital_manager.exception.ApiException;
import com.example.hospital_manager.model.*;
import com.example.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public AppointmentResponse bookAppointment(
            AppointmentRequest request, String username) {

        AppUser currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "User not found"));

        Patient patient = patientRepository.findAll().stream()
                .filter(p -> p.getUser() != null &&
                        p.getUser().getId().equals(currentUser.getId()))
                .findFirst()
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND,
                        "Patient profile not found"));

        if (!patient.getId().equals(request.patientId())) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN,
                    "You can only book appointments for yourself");
        }

        AppUser doctor = userRepository.findById(request.doctorId())
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        if (doctor.getRole() != Role.DOCTOR) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST, "Selected user is not a doctor");
        }

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(request.appointmentDate());
        appointment.setReason(request.reason());
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        return toResponse(appointmentRepository.save(appointment));
    }

    public List<AppointmentResponse> getDoctorAppointments(
            String username) {

        AppUser doctor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Doctor not found"));

        return appointmentRepository.findByDoctorId(doctor.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<AppointmentResponse> getAllAppointments() {
        return appointmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AppointmentResponse updateStatus(
            Long id, StatusRequest request, String username) {

        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Appointment not found"));

        if (!appointment.getDoctor().getUsername().equals(username)) {
            throw new ApiException(
                    HttpStatus.FORBIDDEN,
                    "You cannot modify another doctor's appointment");
        }

        appointment.setStatus(request.status());

        return toResponse(appointmentRepository.save(appointment));
    }

    private AppointmentResponse toResponse(Appointment appointment) {

        return new AppointmentResponse(
                appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getPatient().getFullName(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getFullName(),
                appointment.getAppointmentDate(),
                appointment.getStatus().name(),
                appointment.getReason()
        );
    }
}
