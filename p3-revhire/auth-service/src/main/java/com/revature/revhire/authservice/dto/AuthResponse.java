package com.revature.revhire.authservice.dto;

import com.revature.revhire.authservice.enums.Role;

import java.util.Objects;

public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private Boolean profileCompleted;
    private String message;

    public AuthResponse() {
    }

    public AuthResponse(String token, String type, Long userId, String username, String email,
                       Role role, Boolean profileCompleted, String message) {
        this.token = token;
        this.type = type;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.profileCompleted = profileCompleted;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Boolean getProfileCompleted() {
        return profileCompleted;
    }

    public void setProfileCompleted(Boolean profileCompleted) {
        this.profileCompleted = profileCompleted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthResponse that = (AuthResponse) o;
        return Objects.equals(token, that.token) &&
               Objects.equals(type, that.type) &&
               Objects.equals(userId, that.userId) &&
               Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               role == that.role &&
               Objects.equals(profileCompleted, that.profileCompleted) &&
               Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, type, userId, username, email, role, profileCompleted, message);
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "token='" + token + '\'' +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", profileCompleted=" + profileCompleted +
                ", message='" + message + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String token;
        private String type = "Bearer";
        private Long userId;
        private String username;
        private String email;
        private Role role;
        private Boolean profileCompleted;
        private String message;

        public Builder token(String token) {
            this.token = token;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder userId(Long userId) {
            this.userId = userId;
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

        public Builder profileCompleted(Boolean profileCompleted) {
            this.profileCompleted = profileCompleted;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public AuthResponse build() {
            return new AuthResponse(token, type, userId, username, email, role, profileCompleted, message);
        }
    }
}
