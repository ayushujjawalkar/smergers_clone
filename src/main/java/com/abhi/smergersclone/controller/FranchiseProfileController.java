package com.abhi.smergersclone.controller;

import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
import com.abhi.smergersclone.entity.FranchiseProfile;
import com.abhi.smergersclone.service.FranchiseProfileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/franchise-profiles")
public class FranchiseProfileController {

    private final FranchiseProfileService franchiseProfileService;

    public FranchiseProfileController(FranchiseProfileService franchiseProfileService) {
        this.franchiseProfileService = franchiseProfileService;
    }

    // Create Franchise Profile
    @PostMapping ("/createFranchiseProfile")
    public ResponseEntity<FranchiseProfileResponseDTO> createFranchiseProfile(
            @Valid @RequestBody FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfileResponseDTO responseDTO = franchiseProfileService.createFranchiseProfile(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Get Franchise Profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<FranchiseProfileResponseDTO> getFranchiseProfileById(
            @PathVariable Long id) {
        FranchiseProfileResponseDTO responseDTO = franchiseProfileService.getFranchiseProfileById(id);
        return ResponseEntity.ok(responseDTO);
    }

    // Get All Franchise Profiles
    @GetMapping ("/getAllFranchiseProfile")
    public ResponseEntity<List<FranchiseProfileResponseDTO>> getAllFranchiseProfiles() {
        List<FranchiseProfileResponseDTO> responseDTOs = franchiseProfileService.getAllFranchiseProfiles();
        return ResponseEntity.ok(responseDTOs);
    }

    // Get All Franchise Profiles with Pagination
    @GetMapping("/paginated")
    public ResponseEntity<Page<FranchiseProfileResponseDTO>> getAllFranchiseProfilesPaginated(
            Pageable pageable) {
        Page<FranchiseProfileResponseDTO> responsePage = franchiseProfileService.getAllFranchiseProfilesPaginated(pageable);
        return ResponseEntity.ok(responsePage);
    }

    // Update Franchise Profile
    @PutMapping("/{id}")
    public ResponseEntity<FranchiseProfileResponseDTO> updateFranchiseProfile(
            @PathVariable Long id,
            @Valid @RequestBody FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfileResponseDTO responseDTO = franchiseProfileService.updateFranchiseProfile(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Delete Franchise Profile
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFranchiseProfile(
            @PathVariable Long id) {
        franchiseProfileService.deleteFranchiseProfile(id);
        return ResponseEntity.noContent().build();
    }

    // Upload Brand Logo
    @PostMapping("/{id}/brand-logo")
    public ResponseEntity<FranchiseProfileResponseDTO> uploadBrandLogo(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {
        FranchiseProfileResponseDTO responseDTO = franchiseProfileService.uploadBrandLogo(id, file);
        return ResponseEntity.ok(responseDTO);
    }

    // Search Franchise Profiles
    @GetMapping("/search")
    public ResponseEntity<List<FranchiseProfileResponseDTO>> searchFranchiseProfiles(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String industry,
            @RequestParam(required = false) FranchiseProfile.OpportunityType opportunityType) {
        List<FranchiseProfileResponseDTO> responseDTOs = franchiseProfileService
                .searchFranchiseProfiles(brandName, industry, opportunityType);
        return ResponseEntity.ok(responseDTOs);
    }
}
