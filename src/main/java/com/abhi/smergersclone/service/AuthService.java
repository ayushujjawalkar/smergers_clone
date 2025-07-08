package com.abhi.smergersclone.service;


import com.abhi.smergersclone.dto.AuthRequest;
import com.abhi.smergersclone.dto.AuthResponse;
import com.abhi.smergersclone.dto.RegisterRequest;
import com.abhi.smergersclone.entity.User;
import com.abhi.smergersclone.repository.UserRepository;
import com.abhi.smergersclone.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    //injection for otp service.
    private final OtpService otpService;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public String register(RegisterRequest request) {

        if (!otpService.isVerified(request.getEmail())) {
            throw new RuntimeException("Email not verified. Please verify OTP.");
        }

        // Check if user exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User already exists.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        userRepository.save(user);
        return jwtUtil.generateToken(user.getEmail());
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
