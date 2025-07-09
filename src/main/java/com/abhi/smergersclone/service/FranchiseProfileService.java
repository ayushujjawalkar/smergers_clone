//package com.abhi.smergersclone.service;
//import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
//import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
//import com.abhi.smergersclone.entity.FranchiseProfile;
//import com.abhi.smergersclone.exception.ResourceNotFoundException;
//import com.abhi.smergersclone.mapper.FranchiseProfileMapper;
//import com.abhi.smergersclone.repository.FranchiseProfileRepository;
//import lombok.RequiredArgsConstructor;
//import org.hibernate.query.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class FranchiseProfileService {
//
//    private final FranchiseProfileRepository franchiseProfileRepository;
//    private final FranchiseProfileMapper franchiseProfileMapper;
//
//    // Create
//    @Transactional
//    public FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO) {
//        FranchiseProfile profile = franchiseProfileMapper.toEntity(requestDTO);
//        FranchiseProfile savedProfile = franchiseProfileRepository.save(profile);
//        return franchiseProfileMapper.toResponseDTO(savedProfile);
//    }
//
//    // Read (Single)
//    @Transactional(readOnly = true)
//    public FranchiseProfileResponseDTO getFranchiseProfileById(Long id) {
//        return franchiseProfileRepository.findById(id)
//                .map(franchiseProfileMapper::toResponseDTO)
//                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
//    }
//
//    // Read (All)
//    @Transactional(readOnly = true)
//    public List<FranchiseProfileResponseDTO> getAllFranchiseProfiles() {
//        return franchiseProfileRepository.findAll()
//                .stream()
//                .map(franchiseProfileMapper::toResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    // Update
//    @Transactional
//    public FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO) {
//        return franchiseProfileRepository.findById(id)
//                .map(existingProfile -> {
//                    FranchiseProfile updatedProfile = franchiseProfileMapper.toEntity(requestDTO);
//                    updatedProfile.setId(id); // Ensure existing ID is preserved
//                    FranchiseProfile savedProfile = franchiseProfileRepository.save(updatedProfile);
//                    return franchiseProfileMapper.toResponseDTO(savedProfile);
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
//    }
//
//    // Delete
//    @Transactional
//    public void deleteFranchiseProfile(Long id) {
//        if (!franchiseProfileRepository.existsById(id)) {
//            throw new ResourceNotFoundException("FranchiseProfile not found with id: " + id);
//        }
//        franchiseProfileRepository.deleteById(id);
//    }
//
//    // Business Logic Examples
//    @Transactional(readOnly = true)
//    public List<FranchiseProfileResponseDTO> getProfilesByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan) {
//        return franchiseProfileRepository.findByPaymentPlan(paymentPlan)
//                .stream()
//                .map(franchiseProfileMapper::toResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public FranchiseProfileResponseDTO updateBrandLogo(Long id, String logoPath)
//    {
//        FranchiseProfile profile = franchiseProfileRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found"));
//
//        profile.setBrandLogoPath(logoPath);
//        FranchiseProfile updatedProfile = franchiseProfileRepository.save(profile);
//        return franchiseProfileMapper.toResponseDTO(updatedProfile);
//    }
//
//
//    //new added
//
//    public Page<FranchiseProfileResponseDTO> getAllFranchiseProfilesPaginated(Pageable pageable) {
//        return franchiseProfileRepository.findAll(pageable)
//                .map(franchiseProfileMapper::toResponseDTO);
//    }
//
//    public FranchiseProfileResponseDTO uploadBrandLogo(Long id, MultipartFile file) {
//        FranchiseProfile profile = franchiseProfileRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Franchise profile not found"));
//
//        // Implement file storage logic here
//        String filePath = fileStorageService.storeFile(file);
//        profile.setBrandLogoPath(filePath);
//
//        FranchiseProfile updatedProfile = franchiseProfileRepository.save(profile);
//        return franchiseProfileMapper.toResponseDTO(updatedProfile);
//    }
//
//    public List<FranchiseProfileResponseDTO> searchFranchiseProfiles(
//            String brandName,
//            String industry,
//            String opportunityType) {
//        // Implement your search logic here
//        return franchiseProfileRepository.findByBrandNameContainingAndIndustryAndOpportunityType(
//                        brandName, industry,
//                        FranchiseProfile.OpportunityType.valueOf(opportunityType))
//                .stream()
//                .map(franchiseProfileMapper::toResponseDTO)
//                .toList();
//    }
//}
package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.*;
import com.abhi.smergersclone.entity.FranchiseProfile;
import com.abhi.smergersclone.exception.ResourceNotFoundException;
import com.abhi.smergersclone.mapper.FranchiseProfileMapper;
import com.abhi.smergersclone.repository.FranchiseProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FranchiseProfileService {

    private final FranchiseProfileRepository repository;
    private final FranchiseProfileMapper mapper;

    @Transactional
    public FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfile profile = mapper.toEntity(requestDTO);
        FranchiseProfile savedProfile = repository.save(profile);
        return mapper.toResponseDTO(savedProfile);
    }

    @Transactional(readOnly = true)
    public FranchiseProfileResponseDTO getFranchiseProfileById(Long id) {
        return repository.findById(id)
                .map(mapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<FranchiseProfileResponseDTO> getAllFranchiseProfiles() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // Fixed pagination method
    @Transactional(readOnly = true)
    public Page<FranchiseProfileResponseDTO> getAllFranchiseProfilesPaginated(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponseDTO);
    }

    @Transactional
    public FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO) {
        return repository.findById(id)
                .map(existingProfile -> {
                    FranchiseProfile updatedProfile = mapper.toEntity(requestDTO);
                    updatedProfile.setId(id); // Preserve existing ID
                    FranchiseProfile savedProfile = repository.save(updatedProfile);
                    return mapper.toResponseDTO(savedProfile);
                })
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found with id: " + id));
    }

    @Transactional
    public void deleteFranchiseProfile(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("FranchiseProfile not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public FranchiseProfileResponseDTO uploadBrandLogo(Long id, MultipartFile file) {
        FranchiseProfile profile = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FranchiseProfile not found"));

        // Implement actual file storage logic here
        String filePath = "stored/path/" + file.getOriginalFilename(); // Replace with real storage
        profile.setBrandLogoPath(filePath);

        FranchiseProfile updatedProfile = repository.save(profile);
        return mapper.toResponseDTO(updatedProfile);
    }

    @Transactional(readOnly = true)
    public List<FranchiseProfileResponseDTO> searchFranchiseProfiles(
            String brandName,
            String industry,
            FranchiseProfile.OpportunityType opportunityType) {
        return repository.findByBrandNameContainingAndIndustryAndOpportunityType(
                        brandName, industry, opportunityType)
                .stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}