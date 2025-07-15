package com.abhi.smergersclone.controller;
import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import com.abhi.smergersclone.service.MemberProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/member-profiles")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    /**
     * Create new member profile
     */
    @PostMapping
    public ResponseEntity<MemberProfileResponseDTO> createProfile(@RequestBody MemberProfileRequestDTO requestDTO) {
        MemberProfileResponseDTO response = memberProfileService.createProfile(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update existing profile
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberProfileResponseDTO> updateProfile(
            @PathVariable Long id,
            @RequestBody MemberProfileRequestDTO requestDTO) {
        MemberProfileResponseDTO response = memberProfileService.updateProfile(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Get profile by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberProfileResponseDTO> getProfile(@PathVariable Long id) {
        MemberProfileResponseDTO response = memberProfileService.getProfile(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all profiles with pagination
     */
    @GetMapping
    public ResponseEntity<Page<MemberProfileResponseDTO>> getAllProfiles(Pageable pageable) {
        Page<MemberProfileResponseDTO> response = memberProfileService.getAllProfiles(pageable);
        return ResponseEntity.ok(response);
    }

    /**
     * Activate membership plan for a profile
     */
    @PostMapping("/{id}/activate-plan")
    public ResponseEntity<MemberProfileResponseDTO> activatePlan(
            @PathVariable Long id,
            @RequestParam String planType,
            @RequestParam(defaultValue = "0") String amountPaid) {
        MemberProfileResponseDTO response = memberProfileService.activatePlan(id, planType, amountPaid);
        return ResponseEntity.ok(response);
    }
}
