

package com.example.hospital_manager.service;

import com.example.hospital_manager.exception.ApiException;
import com.example.hospital_manager.model.Patient;
import com.example.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ApiException(
                        HttpStatus.NOT_FOUND, "Patient not found"));
    }

    public List<Patient> searchPatients(String name) {
        return patientRepository.findByFullNameContainingIgnoreCase(name);
    }

    public Patient updatePatient(Long id, Patient updated) {

        Patient patient = getPatient(id);

        patient.setFullName(updated.getFullName());
        patient.setEmail(updated.getEmail());
        patient.setPhone(updated.getPhone());
        patient.setAddress(updated.getAddress());

        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {

        Patient patient = getPatient(id);

        if (appointmentRepository.existsByPatientId(id) ||
                medicalRecordRepository.existsByPatientId(id)) {

            throw new ApiException(
                    HttpStatus.CONFLICT,
                    "Cannot delete patient with appointments or medical records"
            );
        }

        patientRepository.delete(patient);
    }
}
