package com.abhi.smergersclone.controller;
import com.abhi.smergersclone.dto.BusinessProfileRequestDTO;
import com.abhi.smergersclone.dto.BusinessProfileResponseDTO;
import com.abhi.smergersclone.service.BusinessProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/business-profiles")
public class BusinessProfileController {

    private final BusinessProfileService businessProfileService;

    public BusinessProfileController(BusinessProfileService businessProfileService) {
        this.businessProfileService = businessProfileService;
    }

    @PostMapping (("/createBusinessProfile"))
    public ResponseEntity<BusinessProfileResponseDTO> createBusinessProfile(
            @Valid @RequestBody BusinessProfileRequestDTO requestDTO) {
        BusinessProfileResponseDTO responseDTO = businessProfileService.createBusinessProfile(requestDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping ("/getAllBusinessProfile")
    public ResponseEntity<List<BusinessProfileResponseDTO>> getAllBusinessProfiles() {
        List<BusinessProfileResponseDTO> profiles = businessProfileService.getAllBusinessProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessProfileResponseDTO> getBusinessProfileById(@PathVariable Long id) {
        BusinessProfileResponseDTO profile = businessProfileService.getBusinessProfileById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BusinessProfileResponseDTO> updateBusinessProfile(
            @PathVariable Long id,
            @Valid @RequestBody BusinessProfileRequestDTO requestDTO) {
        BusinessProfileResponseDTO updatedProfile = businessProfileService.updateBusinessProfile(id, requestDTO);
        return ResponseEntity.ok(updatedProfile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBusinessProfile(@PathVariable Long id) {
        businessProfileService.deleteBusinessProfile(id);
        return ResponseEntity.noContent().build();
    }

    // Document related endpoints
    @GetMapping("/{profileId}/documents")
    public ResponseEntity<List<BusinessProfileResponseDTO.DocumentDTO>> getDocumentsByProfileId(
            @PathVariable Long profileId) {
        List<BusinessProfileResponseDTO.DocumentDTO> documents =
                businessProfileService.getDocumentsByProfileId(profileId);
        return ResponseEntity.ok(documents);
    }

    @PostMapping("/{profileId}/documents")
    public ResponseEntity<BusinessProfileResponseDTO.DocumentDTO> addDocumentToProfile(
            @PathVariable Long profileId,
            @RequestBody BusinessProfileResponseDTO.DocumentDTO documentDTO) {
        BusinessProfileResponseDTO.DocumentDTO savedDocument =
                businessProfileService.addDocumentToProfile(profileId, documentDTO);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long documentId) {
        businessProfileService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}