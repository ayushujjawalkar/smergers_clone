package com.abhi.smergersclone.controller;
import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
import com.abhi.smergersclone.service.FranchiseProfileService;
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
    @PostMapping
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


}
