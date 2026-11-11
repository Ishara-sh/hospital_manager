
package com.example.hospital_manager.dto;

import java.time.LocalDateTime;

public record MedicalRecordResponse(
    Long id,
    Long patientId,
    String patientName,
    String doctorName,
    String diagnosis,
    String prescription,
    LocalDateTime createdAt
) {}
