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

    // Fields that controllers expect:
    private Long senderId;
    private String senderName;
    private Long recipientId;
    private String recipientName;
    private LocalDateTime readAt;
    
    @Builder.Default
    private Boolean isRead = false;
    
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    // Helper method for setRead
    public void setRead(boolean read) {
        this.isRead = read;
        if (read) {
            this.readAt = LocalDateTime.now();
        }
    }
    
    public boolean isRead() {
        return isRead != null && isRead;
    }
    
    // Getter for recipientId (controller expects this exact name)
    public Long getRecipientId() {
        return recipientId;
    }
}
