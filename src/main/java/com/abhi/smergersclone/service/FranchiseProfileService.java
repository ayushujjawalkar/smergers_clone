package com.abhi.smergersclone.service;
import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
import com.abhi.smergersclone.entity.FranchiseProfile;
import com.abhi.smergersclone.exception.ResourceNotFoundException;
import com.abhi.smergersclone.mapper.FranchiseProfileMapper;
import com.abhi.smergersclone.repository.FranchiseProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranchiseProfileService {

    private final FranchiseProfileRepository franchiseProfileRepository;
    private final FranchiseProfileMapper franchiseProfileMapper;

    // Create
    @Transactional
    public FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfile profile = franchiseProfileMapper.toEntity(requestDTO);
        FranchiseProfile savedProfile = franchiseProfileRepository.save(profile);
        return franchiseProfileMapper.toResponseDTO(savedProfile);
    }

    // Read (Single)
    @Transactional(readOnly = true)
    public FranchiseProfileResponseDTO getFranchiseProfileById(Long id) {
        return franchiseProfileRepository.findById(id)
                .map(franchiseProfileMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
    }

    // Read (All)
    @Transactional(readOnly = true)
    public List<FranchiseProfileResponseDTO> getAllFranchiseProfiles() {
        return franchiseProfileRepository.findAll()
                .stream()
                .map(franchiseProfileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Update
    @Transactional
    public FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO) {
        return franchiseProfileRepository.findById(id)
                .map(existingProfile -> {
                    FranchiseProfile updatedProfile = franchiseProfileMapper.toEntity(requestDTO);
                    updatedProfile.setId(id); // Ensure existing ID is preserved
                    FranchiseProfile savedProfile = franchiseProfileRepository.save(updatedProfile);
                    return franchiseProfileMapper.toResponseDTO(savedProfile);
                })
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
    }

    // Delete
    @Transactional
    public void deleteFranchiseProfile(Long id) {
        if (!franchiseProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("FranchiseProfile not found with id: " + id);
        }
        franchiseProfileRepository.deleteById(id);
    }

    // Business Logic Examples
    @Transactional(readOnly = true)
    public List<FranchiseProfileResponseDTO> getProfilesByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan) {
        return franchiseProfileRepository.findByPaymentPlan(paymentPlan)
                .stream()
                .map(franchiseProfileMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FranchiseProfileResponseDTO updateBrandLogo(Long id, String logoPath) {
        FranchiseProfile profile = franchiseProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found"));

        profile.setBrandLogoPath(logoPath);
        FranchiseProfile updatedProfile = franchiseProfileRepository.save(profile);
        return franchiseProfileMapper.toResponseDTO(updatedProfile);
    }
}