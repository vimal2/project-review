package com.revature.revhire.authservice.dto;

import com.revature.revhire.authservice.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class RegisterRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    private String fullName;

    private String mobileNumber;

    @NotBlank(message = "Security question is required")
    private String securityQuestion;

    @NotBlank(message = "Security answer is required")
    private String securityAnswer;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password, Role role, String fullName,
                          String mobileNumber, String securityQuestion, String securityAnswer) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RegisterRequest that = (RegisterRequest) o;
        return Objects.equals(username, that.username) &&
               Objects.equals(email, that.email) &&
               Objects.equals(password, that.password) &&
               role == that.role &&
               Objects.equals(fullName, that.fullName) &&
               Objects.equals(mobileNumber, that.mobileNumber) &&
               Objects.equals(securityQuestion, that.securityQuestion) &&
               Objects.equals(securityAnswer, that.securityAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, role, fullName, mobileNumber, securityQuestion, securityAnswer);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", fullName='" + fullName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                '}';
    }
}
