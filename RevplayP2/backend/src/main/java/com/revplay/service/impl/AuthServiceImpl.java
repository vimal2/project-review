package com.revplay.service.impl;

import com.revplay.config.JwtTokenProvider;
import com.revplay.dto.request.LoginRequest;
import com.revplay.dto.request.RegisterRequest;
import com.revplay.dto.response.AuthResponse;
import com.revplay.entity.Artist;
import com.revplay.entity.Role;
import com.revplay.entity.User;
import com.revplay.exception.UnauthorizedException;
import com.revplay.repository.ArtistRepository;
import com.revplay.repository.UserRepository;
import com.revplay.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class    AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ArtistRepository artistRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(UserRepository userRepository,
                           ArtistRepository artistRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.artistRepository = artistRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.countByUsername(request.getUsername()) > 0) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.countByEmail(request.getEmail()) > 0) {
            throw new IllegalArgumentException("Email already exists");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.USER;

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);

        String token = jwtTokenProvider.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        Long artistId = resolveArtistId(user);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name(),
                artistId
        );
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        String identifier = request.getUsername();

        User user = identifier.contains("@")
                ? userRepository.findByEmail(identifier).orElse(null)
                : userRepository.findByUsername(identifier).orElse(null);

        if (user == null && identifier.contains("@")) {
            user = migrateArtistToUserIfValid(identifier, request.getPassword());
        }

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        Long artistId = resolveArtistId(user);

        return new AuthResponse(
                token,
                user.getUsername(),
                user.getRole().name(),
                artistId
        );
    }

    private User migrateArtistToUserIfValid(String email, String rawPassword) {
        Optional<Artist> optionalArtist = artistRepository.findByEmail(email);
        if (optionalArtist.isEmpty()) {
            return null;
        }

        Artist artist = optionalArtist.get();
        String storedPassword = artist.getPassword();

        boolean validPassword = storedPassword != null &&
                (storedPassword.equals(rawPassword) || encodedPasswordMatches(rawPassword, storedPassword));

        if (!validPassword) {
            return null;
        }

        String username = buildUniqueUsername(artist.getArtistName(), email);
        String encodedPassword = encodedPasswordMatches(rawPassword, storedPassword)
                ? storedPassword
                : passwordEncoder.encode(rawPassword);

        User migratedUser = User.builder()
                .username(username)
                .email(artist.getEmail())
                .password(encodedPassword)
                .role(Role.ARTIST)
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .build();

        return userRepository.save(migratedUser);
    }

    private String buildUniqueUsername(String artistName, String email) {
        String base = sanitizeUsername(artistName);
        if (base.isBlank()) {
            base = sanitizeUsername(email.contains("@") ? email.substring(0, email.indexOf('@')) : email);
        }
        if (base.isBlank()) {
            base = "artist";
        }

        String candidate = base;
        int suffix = 1;
        while (userRepository.countByUsername(candidate) > 0) {
            candidate = base + suffix;
            suffix++;
        }
        return candidate;
    }

    private String sanitizeUsername(String value) {
        if (value == null) {
            return "";
        }
        return value.toLowerCase().replaceAll("[^a-z0-9_]", "");
    }

    private boolean encodedPasswordMatches(String rawPassword, String encodedPassword) {
        try {
            return passwordEncoder.matches(rawPassword, encodedPassword);
        } catch (RuntimeException ex) {
            return false;
        }
    }

    private Long resolveArtistId(User user) {
        if (user.getRole() != Role.ARTIST) {
            return null;
        }

        return artistRepository.findByEmail(user.getEmail())
                .map(Artist::getId)
                .orElseGet(() -> {
                    Artist artist = Artist.builder()
                            .artistName(user.getUsername())
                            .email(user.getEmail())
                            .password(user.getPassword())
                            .build();
                    return artistRepository.save(artist).getId();
                });
    }
}
