package com.revature.revhire.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

public class ForgotPasswordRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Security answer is required")
    private String securityAnswer;

    public ForgotPasswordRequest() {
    }

    public ForgotPasswordRequest(String email, String securityAnswer) {
        this.email = email;
        this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        ForgotPasswordRequest that = (ForgotPasswordRequest) o;
        return Objects.equals(email, that.email) &&
               Objects.equals(securityAnswer, that.securityAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, securityAnswer);
    }

    @Override
    public String toString() {
        return "ForgotPasswordRequest{" +
                "email='" + email + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                '}';
    }
}
