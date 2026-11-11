
package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalRecordRepository
        extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByPatientId(Long patientId);

    boolean existsByPatientId(Long patientId);
}
