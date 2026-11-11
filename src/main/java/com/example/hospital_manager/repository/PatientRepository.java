
package com.example.hospital_manager.repository;

import com.example.hospital_manager.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFullNameContainingIgnoreCase(String name);

    boolean existsByUserId(Long userId);
}
