package com.abhi.smergersclone.controller;
import com.abhi.smergersclone.dto.*;
import com.abhi.smergersclone.entity.MemberProfile;
import com.abhi.smergersclone.service.MemberProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/member-profiles")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    // Create a new member profile
    @PostMapping
    public ResponseEntity<MemberProfileResponseDTO> createMemberProfile(
            @Valid @RequestBody MemberProfileRequestDTO requestDTO) {
        MemberProfileResponseDTO responseDTO = memberProfileService.createMemberProfile(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Get member profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<MemberProfileResponseDTO> getMemberProfileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(memberProfileService.getMemberProfileById(id));
    }

    // Update member profile
    @PutMapping("/{id}")
    public ResponseEntity<MemberProfileResponseDTO> updateMemberProfile(
            @PathVariable Long id,
            @Valid @RequestBody MemberProfileRequestDTO requestDTO) {
        return ResponseEntity.ok(memberProfileService.updateMemberProfile(id, requestDTO));
    }

    // Delete member profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberProfile(
            @PathVariable Long id) {
        memberProfileService.deleteMemberProfile(id);
        return ResponseEntity.noContent().build();
    }

    // Search member profiles with pagination
    @GetMapping("/search")
    public ResponseEntity<Page<MemberProfileResponseDTO>> searchMemberProfiles(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String memberType,
            @RequestParam(required = false) String interests,
            @RequestParam(required = false) List<String> industries,
            @RequestParam(required = false) List<String> locations,
            @RequestParam(required = false) Double minInvestment,
            @RequestParam(required = false) Double maxInvestment,
            @PageableDefault(size = 10) Pageable pageable) {

        List<MemberProfile.InterestType> interestTypes = null;
        if (interests != null && !interests.isEmpty()) {
            interestTypes = Arrays.stream(interests.split(","))
                    .map(String::trim)
                    .map(String::toUpperCase)
                    .map(MemberProfile.InterestType::valueOf)
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(memberProfileService.searchMemberProfiles(
                query,
                memberType != null ? MemberProfile.MemberType.valueOf(memberType.toUpperCase()) : null,
                interestTypes,
                industries,
                locations,
                minInvestment,
                maxInvestment,
                pageable
        ));
    }

    // Upload company logo
    @PostMapping("/{id}/logo")
    public ResponseEntity<MemberProfileResponseDTO> uploadCompanyLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(memberProfileService.uploadCompanyLogo(id, file));
    }

    // Get dashboard statistics
    @GetMapping("/stats")
    public ResponseEntity<MemberProfileStatsDTO> getMemberProfileStats() {
        return ResponseEntity.ok(memberProfileService.getMemberProfileStats());
    }

    // Upgrade membership plan
    @PatchMapping("/{id}/upgrade-plan")
    public ResponseEntity<MemberProfileResponseDTO> upgradeMembershipPlan(
            @PathVariable Long id,
            @RequestParam String plan) {
        return ResponseEntity.ok(memberProfileService.upgradeMembershipPlan(
                id,
                MemberProfile.MembershipPlan.valueOf(plan.toUpperCase())
        ));
    }
}


