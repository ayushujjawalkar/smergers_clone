package com.abhi.smergersclone.controller;

import com.abhi.smergersclone.dto.OtpRequest;
import com.abhi.smergersclone.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<String> generateOtp(@RequestParam String email) {
        otpService.generateOtp(email);
        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpRequest request) {
        boolean verified = otpService.verifyOtp(request.getEmail(), request.getOtp());
        return verified
                ? ResponseEntity.ok("OTP Verified")
                : ResponseEntity.badRequest().body("Invalid or expired OTP");
    }
}
