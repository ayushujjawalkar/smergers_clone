package com.abhi.smergersclone.service;

import com.abhi.smergersclone.entity.OtpVerification;
import com.abhi.smergersclone.repository.OtpVerificationRepository;
import com.abhi.smergersclone.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpVerificationRepository otpRepo;
    private final EmailService emailService;

    public void generateOtp(String email) {
        String otp = OtpGenerator.generate();
        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        OtpVerification otpVerification = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expiryTime(expiry)
                .isVerified(false)
                .build();

        otpRepo.save(otpVerification);
        emailService.sendOtpEmail(email, otp);
    }

    public boolean verifyOtp(String email, String otp) {
        OtpVerification verification = otpRepo.findTopByEmailOrderByIdDesc(email).orElse(null);

        if (verification == null || verification.isVerified() || verification.getExpiryTime().isBefore(LocalDateTime.now()))
            return false;

        if (!verification.getOtp().equals(otp))
            return false;

        verification.setVerified(true);
        otpRepo.save(verification);
        return true;
    }

    public boolean isVerified(String email) {
        return otpRepo.findTopByEmailOrderByIdDesc(email)
                .map(OtpVerification::isVerified)
                .orElse(false);
    }
}
