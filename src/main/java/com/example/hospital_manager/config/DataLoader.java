

package com.example.hospital_manager.config;

import com.example.hospital_manager.model.*;
import com.example.hospital_manager.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadData() {

        return args -> {

            if (!userRepository.existsByUsername("admin")) {

                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("Admin@123"));
                admin.setFullName("Hospital Administrator");
                admin.setEmail("admin@hospital.com");
                admin.setRole(Role.ADMIN);

                userRepository.save(admin);
            }

            if (!userRepository.existsByUsername("doctor")) {

                AppUser doctor = new AppUser();
                doctor.setUsername("doctor");
                doctor.setPassword(passwordEncoder.encode("Doctor@123"));
                doctor.setFullName("Dr. John Smith");
                doctor.setEmail("doctor@hospital.com");
                doctor.setRole(Role.DOCTOR);

                userRepository.save(doctor);
            }

            if (!userRepository.existsByUsername("patient")) {

                AppUser user = new AppUser();
                user.setUsername("patient");
                user.setPassword(passwordEncoder.encode("Patient@123"));
                user.setFullName("John Patient");
                user.setEmail("patient@hospital.com");
                user.setRole(Role.PATIENT);

                userRepository.save(user);

                Patient patient = new Patient();
                patient.setFullName("John Patient");
                patient.setEmail("patient@hospital.com");
                patient.setPhone("0771234567");
                patient.setAddress("Colombo, Sri Lanka");
                patient.setUser(user);

                patientRepository.save(patient);
            }
        };
    }
}
