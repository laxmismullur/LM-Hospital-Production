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
    
    // Core find methods
    Optional<LMDoctor> findByDoctorCode(String doctorCode);
    Optional<LMDoctor> findByEmail(String email);
    
    // Active doctors
    List<LMDoctor> findByActiveTrue();
    
    // Search methods
    List<LMDoctor> findBySpecializationContainingIgnoreCase(String specialization);
    List<LMDoctor> findByDepartmentContainingIgnoreCase(String department);
    List<LMDoctor> findByFullNameContainingIgnoreCase(String fullName);
    
    // Existence checks
    boolean existsByEmail(String email);
    boolean existsByDoctorCode(String doctorCode);
    boolean existsByEmailAndDoctorCodeNot(String email, String doctorCode); // For updates
    
    // Combined queries
    List<LMDoctor> findBySpecializationAndActiveTrue(String specialization);
    List<LMDoctor> findByDepartmentAndActiveTrue(String department);
    
    // Count methods
    long countByActiveTrue();
    long countBySpecialization(String specialization);
    
    // Custom query for search with multiple criteria
    @Query("SELECT d FROM LMDoctor d WHERE " +
           "(:keyword IS NULL OR LOWER(d.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.specialization) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(d.department) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:activeOnly = false OR d.active = true)")
    List<LMDoctor> searchDoctors(@Param("keyword") String keyword, 
                                  @Param("activeOnly") boolean activeOnly);
    
    // Get doctors by multiple IDs
    List<LMDoctor> findByIdIn(List<Long> ids);
    
    // Order by name
    List<LMDoctor> findByActiveTrueOrderByFullNameAsc();
    List<LMDoctor> findAllByOrderByFullNameAsc();
}
