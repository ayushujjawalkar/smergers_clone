package com.abhi.smergersclone.FRANCHISE_PROFILE;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/franchise-profiles")
public class FranchiseProfileController {

    private final FranchiseProfileService franchiseProfileService;

    public FranchiseProfileController(FranchiseProfileService franchiseProfileService) {
        this.franchiseProfileService = franchiseProfileService;
    }

    /**
     * Create a new Franchise Profile
     */
    @PostMapping("/createFranchiseProfile")
    public ResponseEntity<FranchiseProfileResponseDTO> createFranchiseProfile(
            @RequestBody FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfileResponseDTO response = franchiseProfileService.createFranchiseProfile(requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing Franchise Profile by ID
     */
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseProfileResponseDTO> updateFranchiseProfile(
            @PathVariable Long id,
            @RequestBody FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfileResponseDTO response = franchiseProfileService.updateFranchiseProfile(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    /**
     * Get a Franchise Profile by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseProfileResponseDTO> getFranchiseProfileById(@PathVariable Long id) {
        FranchiseProfileResponseDTO response = franchiseProfileService.getFranchiseProfileById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Get All Franchise Profiles
     */
    @GetMapping
    public ResponseEntity<List<FranchiseProfileResponseDTO>> getAllFranchiseProfiles() {
        List<FranchiseProfileResponseDTO> profiles = franchiseProfileService.getAllFranchiseProfiles();
        return ResponseEntity.ok(profiles);
    }

    /**
     * Delete a Franchise Profile by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFranchiseProfile(@PathVariable Long id) {
        franchiseProfileService.deleteFranchiseProfile(id);
        return ResponseEntity.ok("Franchise Profile deleted successfully.");
    }

    @GetMapping("/filter")
    public List<FranchiseProfile> filterProfiles(
            @RequestParam(required = false) FranchiseProfile.OpportunityType opportunityType,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) String headquartersLocation) {
        return franchiseProfileService.filterFranchiseProfiles(opportunityType, industry, headquartersLocation);
    }
    @PostMapping("/{id}/click")
    public ResponseEntity<String> incrementClick(@PathVariable Long id) {
        franchiseProfileService.incrementClick(id);
        return ResponseEntity.ok("Click count updated successfully");
    }
    // GET /franchise-profiles/filterByInvestmentRange?min=500000&max=1500000
    @GetMapping("/filterByInvestmentRange")
    public ResponseEntity<List<FranchiseProfile>> filterProfiles(
            @RequestParam Double min,
            @RequestParam Double max) {
        return ResponseEntity.ok(franchiseProfileService.filterByInvestmentRange(min, max));
    }

    @PutMapping("/{id}/verify")
    public ResponseEntity<FranchiseProfile> verifyProfile(@PathVariable Long id) {
        return ResponseEntity.ok(franchiseProfileService.verifyProfile(id));
    }

    @PutMapping("/{id}/unverify")
    public ResponseEntity<FranchiseProfile> unverifyProfile(@PathVariable Long id) {
        return ResponseEntity.ok(franchiseProfileService.unverifyProfile(id));
    }

}
