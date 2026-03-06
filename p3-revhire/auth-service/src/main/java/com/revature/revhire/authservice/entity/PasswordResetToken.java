package com.revature.revhire.authservice.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryAt;

    public PasswordResetToken() {
    }

    public PasswordResetToken(Long id, String token, User user, LocalDateTime expiryAt) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.expiryAt = expiryAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiryAt() {
        return expiryAt;
    }

    public void setExpiryAt(LocalDateTime expiryAt) {
        this.expiryAt = expiryAt;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PasswordResetToken that = (PasswordResetToken) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(token, that.token) &&
               Objects.equals(user, that.user) &&
               Objects.equals(expiryAt, that.expiryAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, user, expiryAt);
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryAt=" + expiryAt +
                '}';
    }
}
