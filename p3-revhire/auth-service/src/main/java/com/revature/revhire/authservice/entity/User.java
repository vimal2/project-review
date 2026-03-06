package com.revature.revhire.authservice.entity;

import com.revature.revhire.authservice.enums.EmploymentStatus;
import com.revature.revhire.authservice.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required")
    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Column(length = 100)
    private String fullName;

    @Column(length = 15)
    private String mobileNumber;

    @Column(length = 255)
    private String securityQuestion;

    @Column(length = 255)
    private String securityAnswer;

    @Column(length = 100)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EmploymentStatus employmentStatus;

    @Column(nullable = false)
    private Boolean profileCompleted = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    public User(Long id, String username, String email, String password, Role role, String fullName,
                String mobileNumber, String securityQuestion, String securityAnswer, String location,
                EmploymentStatus employmentStatus, Boolean profileCompleted, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.location = location;
        this.employmentStatus = employmentStatus;
        this.profileCompleted = profileCompleted;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Boolean getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(username, user.username) &&
               Objects.equals(email, user.email) &&
               Objects.equals(password, user.password) &&
               role == user.role &&
               Objects.equals(fullName, user.fullName) &&
               Objects.equals(mobileNumber, user.mobileNumber) &&
               Objects.equals(securityQuestion, user.securityQuestion) &&
               Objects.equals(securityAnswer, user.securityAnswer) &&
               Objects.equals(location, user.location) &&
               employmentStatus == user.employmentStatus &&
               Objects.equals(profileCompleted, user.profileCompleted) &&
               Objects.equals(createdAt, user.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, role, fullName, mobileNumber,
                          securityQuestion, securityAnswer, location, employmentStatus,
                          profileCompleted, createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", location='" + location + '\'' +
                ", employmentStatus=" + employmentStatus +
                ", profileCompleted=" + profileCompleted +
                ", createdAt=" + createdAt +
                '}';
    }
}
