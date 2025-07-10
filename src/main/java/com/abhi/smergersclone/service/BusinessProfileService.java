package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.BusinessProfileRequestDTO;
import com.abhi.smergersclone.dto.BusinessProfileResponseDTO;
import com.abhi.smergersclone.entity.BusinessProfile;
import com.abhi.smergersclone.entity.Document;
import com.abhi.smergersclone.entity.User;
import com.abhi.smergersclone.exception.ResourceNotFoundException;
import com.abhi.smergersclone.repository.BusinessProfileRepository;
import com.abhi.smergersclone.repository.DocumentRepository;
import com.abhi.smergersclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessProfileService {

    private final BusinessProfileRepository businessProfileRepository;
    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;

    @Transactional
    public BusinessProfileResponseDTO createBusinessProfile(BusinessProfileRequestDTO requestDTO) {
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.getUserId()));

        BusinessProfile profile = new BusinessProfile();
        mapRequestToEntity(requestDTO, profile);
        profile.setUser(user);
        profile.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus() : "DRAFT");

        BusinessProfile savedProfile = businessProfileRepository.save(profile);
        return mapToResponseDTO(savedProfile);
    }

    @Transactional(readOnly = true)
    public List<BusinessProfileResponseDTO> getAllBusinessProfiles() {
        return businessProfileRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BusinessProfileResponseDTO getBusinessProfileById(Long id) {
        BusinessProfile profile = businessProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business profile not found with id: " + id));
        return mapToResponseDTO(profile);
    }

    @Transactional
    public BusinessProfileResponseDTO updateBusinessProfile(Long id, BusinessProfileRequestDTO requestDTO) {
        BusinessProfile profile = businessProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Business profile not found with id: " + id));

        mapRequestToEntity(requestDTO, profile);
        BusinessProfile updatedProfile = businessProfileRepository.save(profile);
        return mapToResponseDTO(updatedProfile);
    }

    @Transactional
    public void deleteBusinessProfile(Long id) {
        if (!businessProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Business profile not found with id: " + id);
        }
        businessProfileRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public List<BusinessProfileResponseDTO.DocumentDTO> getDocumentsByProfileId(Long profileId) {
        List<Document> documents = documentRepository.findByProfileId(profileId);
        return documents.stream()
                .map(this::mapDocumentToDTO)
                .collect(Collectors.toList());
    }
    @Transactional
    public BusinessProfileResponseDTO.DocumentDTO addDocumentToProfile(Long profileId,
                                                                       BusinessProfileResponseDTO.DocumentDTO documentDTO) {
        BusinessProfile profile = businessProfileRepository.findById(profileId)
                .orElseThrow(() -> new ResourceNotFoundException("Business profile not found with id: " + profileId));

        Document document = new Document();
        document.setDocumentType(documentDTO.getDocumentType());
        document.setFilePath(documentDTO.getFilePath());
        document.setProfile(profile);

        Document savedDocument = documentRepository.save(document);
        return mapDocumentToDTO(savedDocument);
    }
    @Transactional
    public void deleteDocument(Long documentId) {
        if (!documentRepository.existsById(documentId)) {
            throw new ResourceNotFoundException("Document not found with id: " + documentId);
        }
        documentRepository.deleteById(documentId);
    }


    private Set<BusinessProfileResponseDTO.DocumentDTO> mapDocumentsToDTOs(Set<Document> documents) {
        if (documents == null || documents.isEmpty()) {
            return new HashSet<>();
        }
        return documents.stream()
                .map(this::mapDocumentToDTO)
                .collect(Collectors.toSet());
    }



    private void mapRequestToEntity(BusinessProfileRequestDTO requestDTO, BusinessProfile profile) {
        profile.setName(requestDTO.getName());
        profile.setCompanyName(requestDTO.getCompanyName());
        profile.setEmail(requestDTO.getEmail());
        profile.setMobile(requestDTO.getMobile());
        profile.setBusinessType(requestDTO.getBusinessType());
        profile.setBusinessEstablished(requestDTO.getBusinessEstablished());
        profile.setIndustry(requestDTO.getIndustry());
        profile.setLocation(requestDTO.getLocation());
        profile.setEmployeesCount(requestDTO.getEmployeesCount());
        profile.setLegalEntityType(requestDTO.getLegalEntityType());
        profile.setDescription(requestDTO.getDescription());
        profile.setProductsServices(requestDTO.getProductsServices());
        profile.setHighlights(requestDTO.getHighlights());
        profile.setFacilityDetails(requestDTO.getFacilityDetails());
        profile.setFundingDetails(requestDTO.getFundingDetails());
        profile.setMonthlySales(requestDTO.getMonthlySales());
        profile.setYearlySales(requestDTO.getYearlySales());
        profile.setProfitMargin(requestDTO.getProfitMargin());
        profile.setAssetsDetails(requestDTO.getAssetsDetails());
        profile.setPhysicalAssetsValue(requestDTO.getPhysicalAssetsValue());
        profile.setPlanType(requestDTO.getPlanType());

        if (requestDTO.getStatus() != null) {
            profile.setStatus(requestDTO.getStatus());
        }
    }

    private BusinessProfileResponseDTO mapToResponseDTO(BusinessProfile profile) {
        return BusinessProfileResponseDTO.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .name(profile.getName())
                .companyName(profile.getCompanyName())
                .email(profile.getEmail())
                .mobile(profile.getMobile())
                .businessType(profile.getBusinessType())
                .businessEstablished(profile.getBusinessEstablished())
                .industry(profile.getIndustry())
                .location(profile.getLocation())
                .employeesCount(profile.getEmployeesCount())
                .legalEntityType(profile.getLegalEntityType())
                .description(profile.getDescription())
                .productsServices(profile.getProductsServices())
                .highlights(profile.getHighlights())
                .facilityDetails(profile.getFacilityDetails())
                .fundingDetails(profile.getFundingDetails())
                .monthlySales(profile.getMonthlySales())
                .yearlySales(profile.getYearlySales())
                .profitMargin(profile.getProfitMargin())
                .assetsDetails(profile.getAssetsDetails())
                .physicalAssetsValue(profile.getPhysicalAssetsValue())
                .planType(profile.getPlanType())
                .status(profile.getStatus())
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .documents(mapDocumentsToDTOs(profile.getDocuments()))
                .build();
    }

    private BusinessProfileResponseDTO.DocumentDTO mapDocumentToDTO(Document document) {
        return new BusinessProfileResponseDTO.DocumentDTO(
                document.getId(),
                document.getDocumentType(),
                document.getFilePath(),
                document.getUploadedAt()
        );
    }
}