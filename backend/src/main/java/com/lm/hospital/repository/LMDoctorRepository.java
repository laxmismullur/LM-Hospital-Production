package com.lm.hospital.repository;

import com.lm.hospital.model.LMDoctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LMDoctorRepository extends JpaRepository<LMDoctor, Long> {
    
    // Core find methods - these work with your entity
    Optional<LMDoctor> findByDoctorCode(String doctorCode);
    Optional<LMDoctor> findByEmail(String email);
    
    // Active doctors filtering
    List<LMDoctor> findByActiveTrue();
    
    // Search methods
    List<LMDoctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<LMDoctor> findByDepartmentContainingIgnoreCase(String department);
    List<LMDoctor> findByFullNameContainingIgnoreCase(String fullName);
    
    // Existence checks (used by LMDataInitializer)
    boolean existsByEmail(String email);
    boolean existsByDoctorCode(String doctorCode);
    
    // Additional useful queries
    Optional<LMDoctor> findByDoctorCodeAndActiveTrue(String doctorCode);
    List<LMDoctor> findBySpecializationAndActiveTrue(String specialization);
    
    // Custom query for search with multiple criteria
    @Query("SELECT d FROM LMDoctor d WHERE " +
           "(:specialization IS NULL OR LOWER(d.specialization) LIKE LOWER(CONCAT('%', :specialization, '%'))) AND " +
           "(:active IS NULL OR d.active = :active)")
    List<LMDoctor> findDoctorsByFilters(@Param("specialization") String specialization,
                                         @Param("active") Boolean active);
}
