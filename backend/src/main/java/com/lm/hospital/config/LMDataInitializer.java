package com.lm.hospital.config;

import com.lm.hospital.model.*;
import com.lm.hospital.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class LMDataInitializer implements CommandLineRunner {

    @Autowired private LMUserRepository userRepository;
    @Autowired private LMDoctorRepository doctorRepository;
    @Autowired private LMPatientRepository patientRepository;
    @Autowired private LMAppointmentRepository appointmentRepository;
    @Autowired private LMMedicalRecordRepository medicalRecordRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            System.out.println("🚀 Starting LM Hospital Data Initialization...");
            
            ensureAdminUser();
            seedDoctors();
            seedPatients();
            seedAppointments();
            seedMedicalRecords();
            
            System.out.println("✅ Data initialization completed successfully!");
        } catch (Exception e) {
            System.err.println("❌ Data initialization FAILED: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Data initialization failed", e);
        }
    }

    private void ensureAdminUser() {
        if (userRepository.findByUsername("lm_admin").isPresent() ||
            userRepository.findByEmail("admin@lmhospital.com").isPresent()) {
            System.out.println("ℹ️ Admin user already exists, skipping...");
            return;
        }

        LMUser admin = LMUser.builder()
                .username("lm_admin")
                .fullName("Admin Kumar")
                .email("admin@lmhospital.com")
                .phone("+91-9876543210")
                .role(LMRole.ADMIN)
                .password(passwordEncoder.encode("admin123"))
                .active(true)
                .build();
        
        userRepository.save(admin);
        System.out.println("✅ Admin user created with username: lm_admin");
    }

    private void seedDoctors() {
        if (doctorRepository.count() > 0) {
            System.out.println("ℹ️ Doctors already exist (" + doctorRepository.count() + " found), skipping...");
            return;
        }

        System.out.println("📋 Creating doctors...");
        
        LMDoctor doctor1 = LMDoctor.builder()
                .doctorCode("LMD-001")
                .fullName("Dr. Priya Sharma")
                .specialization("Cardiology")
                .department("Cardiology")
                .qualification("MD, DM Cardiology")
                .phone("+91-9876543211")
                .email("priya.sharma@lmhospital.com")
                .experience("12 years")
                .consultationFee("800")
                .availability("Mon-Fri 9AM-5PM")
                .active(true)
                .build();
        doctorRepository.save(doctor1);

        LMDoctor doctor2 = LMDoctor.builder()
                .doctorCode("LMD-002")
                .fullName("Dr. Rahul Verma")
                .specialization("Neurology")
                .department("Neurology")
                .qualification("MD, DM Neurology")
                .phone("+91-9876543212")
                .email("rahul.verma@lmhospital.com")
                .experience("8 years")
                .consultationFee("1000")
                .availability("Mon-Wed-Fri 10AM-4PM")
                .active(true)
                .build();
        doctorRepository.save(doctor2);

        LMDoctor doctor3 = LMDoctor.builder()
                .doctorCode("LMD-003")
                .fullName("Dr. Sunita Iyer")
                .specialization("Pediatrics")
                .department("Pediatrics")
                .qualification("MD Pediatrics, PGPN")
                .phone("+91-9876543215")
                .email("sunita.iyer@lmhospital.com")
                .experience("6 years")
                .consultationFee("600")
                .availability("Tue-Thu-Sat 9AM-3PM")
                .active(true)
                .build();
        doctorRepository.save(doctor3);

        LMDoctor doctor4 = LMDoctor.builder()
                .doctorCode("LMD-004")
                .fullName("Dr. Arjun Nair")
                .specialization("Orthopedics")
                .department("Orthopedics")
                .qualification("MS Orthopedics, DNB")
                .phone("+91-9876543216")
                .email("arjun.nair@lmhospital.com")
                .experience("10 years")
                .consultationFee("900")
                .availability("Mon-Fri 8AM-2PM")
                .active(true)
                .build();
        doctorRepository.save(doctor4);

        System.out.println("✅ Created " + doctorRepository.count() + " doctors");
    }

    private void seedPatients() {
        if (patientRepository.count() > 0) {
            System.out.println("ℹ️ Patients already exist (" + patientRepository.count() + " found), skipping...");
            return;
        }

        // IMPORTANT: Fetch doctors by their doctorCode, NOT by hardcoded IDs
        LMDoctor cardiologist = doctorRepository.findByDoctorCode("LMD-001")
                .orElseThrow(() -> new RuntimeException("Cardiologist (LMD-001) not found!"));
        
        LMDoctor neurologist = doctorRepository.findByDoctorCode("LMD-002")
                .orElseThrow(() -> new RuntimeException("Neurologist (LMD-002) not found!"));

        System.out.println("📋 Creating patients...");

        patientRepository.save(LMPatient.builder()
                .patientId("LMP-A1B2C3D4")
                .fullName("Arjun Mehta")
                .dateOfBirth(LocalDate.of(1985, 3, 15))
                .gender("Male")
                .bloodGroup("O+")
                .phone("+91-9123456789")
                .email("arjun.mehta@email.com")
                .address("12, MG Road, Bengaluru")
                .allergies("Penicillin")
                .status(LMPatientStatus.ACTIVE)
                .assignedDoctorId(cardiologist.getId())
                .assignedDoctorName(cardiologist.getFullName())
                .assignedDoctorCode(cardiologist.getDoctorCode())
                .assignedDoctorSpecialization(cardiologist.getSpecialization())
                .emergencyContact("+91-9123456700")
                .build());

        patientRepository.save(LMPatient.builder()
                .patientId("LMP-E5F6G7H8")
                .fullName("Sneha Patel")
                .dateOfBirth(LocalDate.of(1992, 7, 22))
                .gender("Female")
                .bloodGroup("A+")
                .phone("+91-9234567890")
                .email("sneha.patel@email.com")
                .address("45, Koramangala, Bengaluru")
                .allergies("None")
                .status(LMPatientStatus.ADMITTED)
                .assignedDoctorId(cardiologist.getId())
                .assignedDoctorName(cardiologist.getFullName())
                .assignedDoctorCode(cardiologist.getDoctorCode())
                .assignedDoctorSpecialization(cardiologist.getSpecialization())
                .emergencyContact("+91-9234567800")
                .build());

        patientRepository.save(LMPatient.builder()
                .patientId("LMP-I9J0K1L2")
                .fullName("Vikram Nair")
                .dateOfBirth(LocalDate.of(1978, 11, 8))
                .gender("Male")
                .bloodGroup("B-")
                .phone("+91-9345678901")
                .email("vikram.nair@email.com")
                .address("78, Indiranagar, Bengaluru")
                .allergies("Aspirin")
                .status(LMPatientStatus.CRITICAL)
                .assignedDoctorId(neurologist.getId())
                .assignedDoctorName(neurologist.getFullName())
                .assignedDoctorCode(neurologist.getDoctorCode())
                .assignedDoctorSpecialization(neurologist.getSpecialization())
                .emergencyContact("+91-9345678900")
                .build());

        patientRepository.save(LMPatient.builder()
                .patientId("LMP-M3N4O5P6")
                .fullName("Meera Krishnan")
                .dateOfBirth(LocalDate.of(2000, 5, 30))
                .gender("Female")
                .bloodGroup("AB+")
                .phone("+91-9456789012")
                .email("meera.k@email.com")
                .address("23, Whitefield, Bengaluru")
                .allergies("None")
                .status(LMPatientStatus.STABLE)
                .assignedDoctorId(neurologist.getId())
                .assignedDoctorName(neurologist.getFullName())
                .assignedDoctorCode(neurologist.getDoctorCode())
                .assignedDoctorSpecialization(neurologist.getSpecialization())
                .emergencyContact("+91-9456789000")
                .build());

        patientRepository.save(LMPatient.builder()
                .patientId("LMP-Q7R8S9T0")
                .fullName("Ravi Gupta")
                .dateOfBirth(LocalDate.of(1965, 1, 18))
                .gender("Male")
                .bloodGroup("O-")
                .phone("+91-9567890123")
                .email("ravi.gupta@email.com")
                .address("5, HSR Layout, Bengaluru")
                .allergies("Codeine")
                .status(LMPatientStatus.DISCHARGED)
                .assignedDoctorId(cardiologist.getId())
                .assignedDoctorName(cardiologist.getFullName())
                .assignedDoctorCode(cardiologist.getDoctorCode())
                .assignedDoctorSpecialization(cardiologist.getSpecialization())
                .emergencyContact("+91-9567890100")
                .build());

        System.out.println("✅ Created " + patientRepository.count() + " patients");
    }

    private void seedAppointments() {
        if (appointmentRepository.count() > 0) {
            System.out.println("ℹ️ Appointments already exist (" + appointmentRepository.count() + " found), skipping...");
            return;
        }

        // Fetch required entities
        LMDoctor cardiologist = doctorRepository.findByDoctorCode("LMD-001").orElse(null);
        LMDoctor neurologist = doctorRepository.findByDoctorCode("LMD-002").orElse(null);
        
        if (cardiologist == null || neurologist == null) {
            System.out.println("⚠️ Cannot seed appointments: Required doctors not found");
            return;
        }

        System.out.println("📋 Creating appointments...");

        appointmentRepository.save(LMAppointment.builder()
                .patientName("Arjun Mehta")
                .doctorId(cardiologist.getId())
                .doctorName(cardiologist.getFullName())
                .doctorCode(cardiologist.getDoctorCode())
                .department("Cardiology")
                .specialization("Cardiology")
                .appointmentDate(LocalDateTime.now().plusHours(2))
                .reason("Follow-up checkup for hypertension")
                .status(LMAppointmentStatus.CONFIRMED)
                .build());

        appointmentRepository.save(LMAppointment.builder()
                .patientName("Sneha Patel")
                .doctorId(cardiologist.getId())
                .doctorName(cardiologist.getFullName())
                .doctorCode(cardiologist.getDoctorCode())
                .department("Cardiology")
                .specialization("Cardiology")
                .appointmentDate(LocalDateTime.now().plusHours(4))
                .reason("Chest discomfort")
                .status(LMAppointmentStatus.SCHEDULED)
                .build());

        System.out.println("✅ Created " + appointmentRepository.count() + " appointments");
    }

    private void seedMedicalRecords() {
        if (medicalRecordRepository.count() > 0) {
            System.out.println("ℹ️ Medical records already exist (" + medicalRecordRepository.count() + " found), skipping...");
            return;
        }

        LMDoctor cardiologist = doctorRepository.findByDoctorCode("LMD-001").orElse(null);
        
        if (cardiologist == null) {
            System.out.println("⚠️ Cannot seed medical records: Required doctor not found");
            return;
        }

        System.out.println("📋 Creating medical records...");

        medicalRecordRepository.save(LMMedicalRecord.builder()
                .patientName("Arjun Mehta")
                .doctorId(cardiologist.getId())
                .doctorName(cardiologist.getFullName())
                .diagnosis("Hypertension Stage 1")
                .vitals("BP: 145/90, HR: 82, Temp: 98.6°F, SpO2: 97%")
                .prescription("Amlodipine 5mg OD, Losartan 50mg OD")
                .notes("Patient advised to reduce salt and exercise regularly.")
                .labResults("CBC: Normal, Lipid Profile: Borderline")
                .followUpDate("2025-05-15")
                .build());

        System.out.println("✅ Created " + medicalRecordRepository.count() + " medical records");
    }
}
