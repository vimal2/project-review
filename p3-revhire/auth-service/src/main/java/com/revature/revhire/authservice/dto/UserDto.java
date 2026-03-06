package com.revature.revhire.authservice.dto;

import com.revature.revhire.authservice.enums.EmploymentStatus;
import com.revature.revhire.authservice.enums.Role;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserDto {

    private Long id;
    private String username;
    private String email;
    private Role role;
    private String fullName;
    private String mobileNumber;
    private String location;
    private EmploymentStatus employmentStatus;
    private Boolean profileCompleted;
    private LocalDateTime createdAt;

    public UserDto() {
    }

    public UserDto(Long id, String username, String email, Role role, String fullName,
                  String mobileNumber, String location, EmploymentStatus employmentStatus,
                  Boolean profileCompleted, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
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
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) &&
               Objects.equals(username, userDto.username) &&
               Objects.equals(email, userDto.email) &&
               role == userDto.role &&
               Objects.equals(fullName, userDto.fullName) &&
               Objects.equals(mobileNumber, userDto.mobileNumber) &&
               Objects.equals(location, userDto.location) &&
               employmentStatus == userDto.employmentStatus &&
               Objects.equals(profileCompleted, userDto.profileCompleted) &&
               Objects.equals(createdAt, userDto.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, role, fullName, mobileNumber, location,
                          employmentStatus, profileCompleted, createdAt);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", location='" + location + '\'' +
                ", employmentStatus=" + employmentStatus +
                ", profileCompleted=" + profileCompleted +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String username;
        private String email;
        private Role role;
        private String fullName;
        private String mobileNumber;
        private String location;
        private EmploymentStatus employmentStatus;
        private Boolean profileCompleted;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder fullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder mobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder employmentStatus(EmploymentStatus employmentStatus) {
            this.employmentStatus = employmentStatus;
            return this;
        }

        public Builder profileCompleted(Boolean profileCompleted) {
            this.profileCompleted = profileCompleted;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, username, email, role, fullName, mobileNumber, location,
                             employmentStatus, profileCompleted, createdAt);
        }
    }
}
