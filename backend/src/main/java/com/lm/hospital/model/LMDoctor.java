package com.lm.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lm_doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LMDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String doctorCode;

    @Column(nullable = false)
    private String fullName;

    private String specialization;
    private String department;
    private String qualification;
    private String phone;

    @Column(unique = true)
    private String email;

    private String experience;
    
    @Builder.Default
    private String consultationFee = "0";
    
    @Builder.Default
    private String availability = "Mon-Fri 9AM-5PM";
    
    @Builder.Default
    private Boolean active = true;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
