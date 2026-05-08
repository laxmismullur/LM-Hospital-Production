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
    
    // FIELDS THAT CONTROLLERS EXPECT:
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private LMUser user;
    
    private String username;
    private String password;
    private String address;
    
    // Helper methods for userId
    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
    
    public void setUserId(Long userId) {
        // This would need proper implementation - typically you'd fetch the user
        if (userId != null && user == null) {
            user = new LMUser();
            user.setId(userId);
        } else if (user != null) {
            user.setId(userId);
        }
    }
    
    public String getUsername() {
        return username != null ? username : (user != null ? user.getUsername() : null);
    }
    
    public String getPassword() {
        return password != null ? password : (user != null ? user.getPassword() : null);
    }
    
    public void setUsername(String username) {
        this.username = username;
        if (user != null) {
            user.setUsername(username);
        }
    }
    
    public void setPassword(String password) {
        this.password = password;
        if (user != null) {
            user.setPassword(password);
        }
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public boolean isActive() {
        return active != null && active;
    }
}
