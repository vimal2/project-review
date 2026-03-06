package com.revature.revhire.authservice.repository;

import com.revature.revhire.authservice.entity.PasswordResetToken;
import com.revature.revhire.authservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    void deleteByUser(User user);

    void deleteByExpiryAtBefore(LocalDateTime now);
}
