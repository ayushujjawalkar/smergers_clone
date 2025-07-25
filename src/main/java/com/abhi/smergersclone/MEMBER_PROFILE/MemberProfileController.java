package com.abhi.smergersclone.MEMBER_PROFILE;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/member-profiles")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberProfileService;

    /**
     * Create new member profile
     */
    @PostMapping ("/createMemberProfile")
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
    @GetMapping("/getAllProfile")
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

    @GetMapping("/filter-by-interest")
    public ResponseEntity<Page<MemberProfileResponseDTO>> getProfilesByInterest(
            @RequestParam MemberProfile.InterestType interestType,
            Pageable pageable) {
        Page<MemberProfileResponseDTO> response = memberProfileService.getProfilesByInterest(interestType, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/filterByMemberType")
    public ResponseEntity<Page<MemberProfileResponseDTO>> getProfilesByMemberType(
            @RequestParam("memberType") MemberProfile.MemberType memberType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<MemberProfileResponseDTO> result = memberProfileService.getProfilesByMemberType(memberType, pageable);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filterByLocation")
    public ResponseEntity<List<MemberProfileResponseDTO>> filterByLocation(
            @RequestParam String location,
            @RequestParam(defaultValue = "false") boolean searchInInterestedLocations) {

        List<MemberProfileResponseDTO> result = memberProfileService.filterByLocation(location, searchInInterestedLocations);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/filterByIndustry")
    public ResponseEntity<List<MemberProfileResponseDTO>> filterByIndustry(@RequestParam String industry) {
        List<MemberProfileResponseDTO> result = memberProfileService.filterByIndustry(industry);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/click")
    public ResponseEntity<String> incrementClick(@PathVariable Long id) {
        memberProfileService.incrementClick(id);
        return ResponseEntity.ok("Member profile click count updated successfully");
    }
    @PutMapping("/{id}/verify")
    public ResponseEntity<MemberProfile> verifyProfile(@PathVariable Long id) {
        return ResponseEntity.ok(memberProfileService.verifyProfile(id));
    }

    @PutMapping("/{id}/unverify")
    public ResponseEntity<MemberProfile> unverifyProfile(@PathVariable Long id) {
        return ResponseEntity.ok(memberProfileService.unverifyProfile(id));
    }
}
