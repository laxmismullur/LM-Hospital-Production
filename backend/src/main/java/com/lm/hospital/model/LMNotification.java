package com.lm.hospital.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lm_notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LMNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String message;
    private Long userId;
    private String userRole;

    @Builder.Default
    private Boolean isRead = false;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
