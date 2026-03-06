package com.revhire.employerservice.dto;

import java.util.Objects;

public class UserDetailsResponse {
    private Long id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;

    public UserDetailsResponse() {
    }

    public UserDetailsResponse(Long id, String email, String role, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsResponse that = (UserDetailsResponse) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(email, that.email) &&
               Objects.equals(role, that.role) &&
               Objects.equals(firstName, that.firstName) &&
               Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email, role, firstName, lastName);
    }

    @Override
    public String toString() {
        return "UserDetailsResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String email;
        private String role;
        private String firstName;
        private String lastName;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDetailsResponse build() {
            return new UserDetailsResponse(id, email, role, firstName, lastName);
        }
    }
}
