package com.example.productapi.service;

import com.example.productapi.dto.request.LoginRequest;
import com.example.productapi.dto.response.AuthResponse;
import com.example.productapi.entity.RefreshToken;
import com.example.productapi.entity.User;
import com.example.productapi.repository.UserRepository;
import com.example.productapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public User register(String username, String password) {

        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .role("USER")
                .build();

        return userRepository.save(user);
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() ->
                        new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(
                request.password(),
                user.getPassword())) {

            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }

    public AuthResponse refreshToken(String token) {

        RefreshToken oldRefreshToken =
                refreshTokenService.validateRefreshToken(token);

        User user = oldRefreshToken.getUser();

        // Rotate refresh token
        refreshTokenService.revokeToken(oldRefreshToken);

        String accessToken = jwtService.generateToken(
                user.getUsername(),
                user.getRole()
        );

        RefreshToken newRefreshToken =
                refreshTokenService.createRefreshToken(user);

        return new AuthResponse(
                accessToken,
                newRefreshToken.getToken(),
                "Bearer"
        );
    }
}