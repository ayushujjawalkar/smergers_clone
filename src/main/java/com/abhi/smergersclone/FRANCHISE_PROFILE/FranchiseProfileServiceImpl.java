//package com.abhi.smergersclone.FRANCHISE_PROFILE;
//import com.abhi.smergersclone.PLAN_TYPE.PlanFeatureRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class FranchiseProfileServiceImpl implements FranchiseProfileService {
//
//    private final FranchiseProfileRepository repository;
//    private final PlanFeatureRepository planFeatureRepository; // Added
//
//    public FranchiseProfileServiceImpl(FranchiseProfileRepository repository, PlanFeatureRepository planFeatureRepository) {
//        this.repository = repository;
//        this.planFeatureRepository = planFeatureRepository;
//    }
//
//    @Override
//    public FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO) {
//        FranchiseProfile profile = mapToEntity(requestDTO);
//        FranchiseProfile saved = repository.save(profile);
//        return mapToResponseDTO(saved);
//    }
//
//    @Override
//    public FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO) {
//        FranchiseProfile profile = repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Franchise Profile not found with ID: " + id));
//
//        updateEntity(profile, requestDTO);
//
//        FranchiseProfile updated = repository.save(profile);
//        return mapToResponseDTO(updated);
//    }
//
//    @Override
//    public FranchiseProfileResponseDTO getFranchiseProfileById(Long id) {
//        FranchiseProfile profile = repository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Franchise Profile not found with ID: " + id));
//        return mapToResponseDTO(profile);
//    }
//
//    @Override
//    public List<FranchiseProfileResponseDTO> getAllFranchiseProfiles() {
//        return repository.findAll()
//                .stream()
//                .map(this::mapToResponseDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteFranchiseProfile(Long id) {
//        if (!repository.existsById(id)) {
//            throw new EntityNotFoundException("Franchise Profile not found with ID: " + id);
//        }
//        repository.deleteById(id);
//    }
//    @Transactional
//    @Override
//    public void incrementClick(Long profileId) {
//        repository.incrementClickCount(profileId);
//    }
//
//    @Override
//    public List<FranchiseProfile> filterByInvestmentRange(Double min, Double max) {
//        return repository.findByInvestmentRange(min, max);
//    }
//
//    @Override
//    public FranchiseProfile verifyProfile(Long id) {
//        FranchiseProfile profile = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Franchise Profile not found"));
//        profile.setVerified(true);
//        return repository.save(profile);
//    }
//
//    @Override
//    public FranchiseProfile unverifyProfile(Long id) {
//        FranchiseProfile profile = repository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Franchise Profile not found"));
//        profile.setVerified(false);
//        return repository.save(profile);
//    }
//
//    // ===== Mapping Methods =====
//
//    private FranchiseProfile mapToEntity(FranchiseProfileRequestDTO dto) {
//        FranchiseProfile profile = new FranchiseProfile();
//        profile.setAuthorizedPersonName(dto.getAuthorizedPersonName());
//        profile.setOfficialEmail(dto.getOfficialEmail());
//        profile.setMobileNumber(dto.getMobileNumber());
//        profile.setBrandName(dto.getBrandName());
//        profile.setWebsite(dto.getWebsite());
//        profile.setOpportunityType(FranchiseProfile.OpportunityType.valueOf(dto.getOpportunityType()));
//        profile.setIndustry(dto.getIndustry());
//        profile.setAboutBrand(dto.getAboutBrand());
//        profile.setProductsServices(dto.getProductsServices());
//        profile.setStartOperationsDate(dto.getStartOperationsDate());
//        profile.setHeadquartersLocation(dto.getHeadquartersLocation());
//        profile.setGlobalOutletsCount(dto.getGlobalOutletsCount());
//        profile.setExpectationsFromUser(dto.getExpectationsFromUser());
//        profile.setSupportProvided(dto.getSupportProvided());
//        profile.setFranchiseProcedure(dto.getFranchiseProcedure());
//        profile.setExpansionLocations(dto.getExpansionLocations());
//        profile.setFranchiseFormats(dto.getFranchiseFormats()
//                .stream()
//                .map(formatDto -> {
//                    FranchiseProfile.FranchiseFormat format = new FranchiseProfile.FranchiseFormat();
//                    format.setFormatName(formatDto.getFormatName());
//                    format.setMinSqFt(formatDto.getMinSqFt());
//                    format.setMaxSqFt(formatDto.getMaxSqFt());
//                    format.setMinInvestment(formatDto.getMinInvestment());
//                    format.setMaxInvestment(formatDto.getMaxInvestment());
//                    format.setCurrency(formatDto.getCurrency());
//                    format.setBrandFee(formatDto.getBrandFee());
//                    format.setAverageStaffRequired(formatDto.getAverageStaffRequired());
//                    format.setRoyaltyDetails(formatDto.getRoyaltyDetails());
//                    format.setAverageMonthlySales(formatDto.getAverageMonthlySales());
//                    format.setAverageEBITDA(formatDto.getAverageEBITDA());
//                    return format;
//                }).toList());
//        profile.setPaymentPlan(FranchiseProfile.PaymentPlan.valueOf(dto.getPaymentPlan()));
//        return profile;
//    }
//
//    private void updateEntity(FranchiseProfile profile, FranchiseProfileRequestDTO dto) {
//        profile.setAuthorizedPersonName(dto.getAuthorizedPersonName());
//        profile.setOfficialEmail(dto.getOfficialEmail());
//        profile.setMobileNumber(dto.getMobileNumber());
//        profile.setBrandName(dto.getBrandName());
//        profile.setWebsite(dto.getWebsite());
//        profile.setOpportunityType(FranchiseProfile.OpportunityType.valueOf(dto.getOpportunityType()));
//        profile.setIndustry(dto.getIndustry());
//        profile.setAboutBrand(dto.getAboutBrand());
//        profile.setProductsServices(dto.getProductsServices());
//        profile.setStartOperationsDate(dto.getStartOperationsDate());
//        profile.setHeadquartersLocation(dto.getHeadquartersLocation());
//        profile.setGlobalOutletsCount(dto.getGlobalOutletsCount());
//        profile.setExpectationsFromUser(dto.getExpectationsFromUser());
//        profile.setSupportProvided(dto.getSupportProvided());
//        profile.setFranchiseProcedure(dto.getFranchiseProcedure());
//        profile.setExpansionLocations(dto.getExpansionLocations());
//        profile.setFranchiseFormats(dto.getFranchiseFormats()
//                .stream()
//                .map(formatDto -> {
//                    FranchiseProfile.FranchiseFormat format = new FranchiseProfile.FranchiseFormat();
//                    format.setFormatName(formatDto.getFormatName());
//                    format.setMinSqFt(formatDto.getMinSqFt());
//                    format.setMaxSqFt(formatDto.getMaxSqFt());
//                    format.setMinInvestment(formatDto.getMinInvestment());
//                    format.setMaxInvestment(formatDto.getMaxInvestment( ));
//                    format.setCurrency(formatDto.getCurrency());
//                    format.setBrandFee(formatDto.getBrandFee());
//                    format.setAverageStaffRequired(formatDto.getAverageStaffRequired());
//                    format.setRoyaltyDetails(formatDto.getRoyaltyDetails());
//                    format.setAverageMonthlySales(formatDto.getAverageMonthlySales());
//                    format.setAverageEBITDA(formatDto.getAverageEBITDA());
//                    return format;
//                }).toList());
//        profile.setPaymentPlan(FranchiseProfile.PaymentPlan.valueOf(dto.getPaymentPlan()));
//    }
//
//    private FranchiseProfileResponseDTO mapToResponseDTO(FranchiseProfile profile) {
//        FranchiseProfileResponseDTO dto = new FranchiseProfileResponseDTO();
//        dto.setId(profile.getId());
//        dto.setAuthorizedPersonName(profile.getAuthorizedPersonName());
//        dto.setOfficialEmail(profile.getOfficialEmail());
//        dto.setMobileNumber(profile.getMobileNumber());
//        dto.setBrandName(profile.getBrandName());
//        dto.setWebsite(profile.getWebsite());
//        dto.setOpportunityType(profile.getOpportunityType().name());
//        dto.setIndustry(profile.getIndustry());
//        dto.setAboutBrand(profile.getAboutBrand());
//        dto.setProductsServices(profile.getProductsServices());
//        dto.setStartOperationsDate(profile.getStartOperationsDate());
//        dto.setHeadquartersLocation(profile.getHeadquartersLocation());
//        dto.setGlobalOutletsCount(profile.getGlobalOutletsCount());
//        dto.setExpectationsFromUser(profile.getExpectationsFromUser());
//        dto.setSupportProvided(profile.getSupportProvided());
//        dto.setFranchiseProcedure(profile.getFranchiseProcedure());
//        dto.setExpansionLocations(profile.getExpansionLocations());
//        dto.setFranchiseFormats(profile.getFranchiseFormats()
//                .stream()
//                .map(format -> {
//                    FranchiseProfileResponseDTO.FranchiseFormatDTO formatDTO = new FranchiseProfileResponseDTO.FranchiseFormatDTO();
//                    formatDTO.setFormatName(format.getFormatName());
//                    formatDTO.setMinSqFt(format.getMinSqFt());
//                    formatDTO.setMaxSqFt(format.getMaxSqFt());
//                    formatDTO.setMinInvestment(format.getMinInvestment());
//                    formatDTO.setMaxInvestment(format.getMaxInvestment());
//                    formatDTO.setCurrency(format.getCurrency());
//                    formatDTO.setBrandFee(format.getBrandFee());
//                    formatDTO.setAverageStaffRequired(format.getAverageStaffRequired());
//                    formatDTO.setRoyaltyDetails(format.getRoyaltyDetails());
//                    formatDTO.setAverageMonthlySales(format.getAverageMonthlySales());
//                    formatDTO.setAverageEBITDA(format.getAverageEBITDA());
//                    return formatDTO;
//                }).toList());
//        dto.setBrandLogoPath(profile.getBrandLogoPath());
//        dto.setBusinessPhotosPaths(profile.getBusinessPhotosPaths());
//        dto.setBrochuresDocumentsPaths(profile.getBrochuresDocumentsPaths());
//        dto.setProofOfBusinessPath(profile.getProofOfBusinessPath());
//        dto.setPaymentPlan(profile.getPaymentPlan().name());
//
//        // ✅ Fetch plan features and add to response
//        planFeatureRepository.findByPaymentPlan(profile.getPaymentPlan())
//                .ifPresent(planFeature -> dto.setPlanFeatures(planFeature.getFeatures()));
//
//        return dto;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<FranchiseProfile> filterFranchiseProfiles(
//            FranchiseProfile.OpportunityType opportunityType,
//            String industry,
//            String headquartersLocation) {
//
//        return repository.filterProfiles(opportunityType, industry, headquartersLocation);
//    }
//}
package com.abhi.smergersclone.FRANCHISE_PROFILE;

import com.abhi.smergersclone.PLAN_TYPE.PlanFeatureRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FranchiseProfileServiceImpl implements FranchiseProfileService {

    private final FranchiseProfileRepository repository;
    private final PlanFeatureRepository planFeatureRepository;

    public FranchiseProfileServiceImpl(FranchiseProfileRepository repository, PlanFeatureRepository planFeatureRepository) {
        this.repository = repository;
        this.planFeatureRepository = planFeatureRepository;
    }

    @Override
    public FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfile profile = mapToEntity(requestDTO);
        FranchiseProfile saved = repository.save(profile);
        return mapToResponseDTO(saved);
    }

    @Override
    public FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO) {
        FranchiseProfile profile = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Franchise Profile not found with ID: " + id));

        updateEntity(profile, requestDTO);

        FranchiseProfile updated = repository.save(profile);
        return mapToResponseDTO(updated);
    }

    @Override
    public FranchiseProfileResponseDTO getFranchiseProfileById(Long id) {
        FranchiseProfile profile = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Franchise Profile not found with ID: " + id));
        return mapToResponseDTO(profile);
    }

    @Override
    public List<FranchiseProfileResponseDTO> getAllFranchiseProfiles() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFranchiseProfile(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Franchise Profile not found with ID: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    @Override
    public void incrementClick(Long profileId) {
        repository.incrementClickCount(profileId);
    }

    @Override
    public List<FranchiseProfile> filterByInvestmentRange(Double min, Double max) {
        return repository.findByInvestmentRange(min, max);
    }

    @Override
    public FranchiseProfile verifyProfile(Long id) {
        FranchiseProfile profile = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franchise Profile not found"));
        profile.setVerified(true);
        return repository.save(profile);
    }

    @Override
    public FranchiseProfile unverifyProfile(Long id) {
        FranchiseProfile profile = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Franchise Profile not found"));
        profile.setVerified(false);
        return repository.save(profile);
    }

    // ===== Mapping Methods =====

    private FranchiseProfile mapToEntity(FranchiseProfileRequestDTO dto) {
        FranchiseProfile profile = new FranchiseProfile();
        profile.setAuthorizedPersonName(dto.getAuthorizedPersonName());
        profile.setOfficialEmail(dto.getOfficialEmail());
        profile.setMobileNumber(dto.getMobileNumber());
        profile.setBrandName(dto.getBrandName());
        profile.setWebsite(dto.getWebsite());
        profile.setOpportunityType(FranchiseProfile.OpportunityType.valueOf(dto.getOpportunityType().toUpperCase()));
        profile.setIndustry(dto.getIndustry());
        profile.setAboutBrand(dto.getAboutBrand());
        profile.setProductsServices(dto.getProductsServices());
        profile.setStartOperationsDate(dto.getStartOperationsDate());
        profile.setHeadquartersLocation(dto.getHeadquartersLocation());
        profile.setGlobalOutletsCount(dto.getGlobalOutletsCount());
        profile.setExpectationsFromUser(dto.getExpectationsFromUser());
        profile.setSupportProvided(dto.getSupportProvided());
        profile.setFranchiseProcedure(dto.getFranchiseProcedure());
        profile.setExpansionLocations(dto.getExpansionLocations());

        // ✅ Convert Franchise Formats safely
        List<FranchiseProfile.FranchiseFormat> formats = dto.getFranchiseFormats() != null
                ? dto.getFranchiseFormats().stream()
                .map(formatDto -> {
                    FranchiseProfile.FranchiseFormat format = new FranchiseProfile.FranchiseFormat();
                    format.setFormatName(formatDto.getFormatName());
                    format.setMinSqFt(formatDto.getMinSqFt());
                    format.setMaxSqFt(formatDto.getMaxSqFt());
                    format.setMinInvestment(formatDto.getMinInvestment());
                    format.setMaxInvestment(formatDto.getMaxInvestment());
                    format.setCurrency(formatDto.getCurrency());
                    format.setBrandFee(formatDto.getBrandFee());
                    format.setAverageStaffRequired(formatDto.getAverageStaffRequired());
                    format.setRoyaltyDetails(formatDto.getRoyaltyDetails());
                    format.setAverageMonthlySales(formatDto.getAverageMonthlySales());
                    format.setAverageEBITDA(formatDto.getAverageEBITDA());
                    return format;
                }).collect(Collectors.toList())
                : new ArrayList<>();

        profile.setFranchiseFormats(formats);
        profile.setPaymentPlan(FranchiseProfile.PaymentPlan.valueOf(dto.getPaymentPlan().toUpperCase()));
        return profile;
    }

    private void updateEntity(FranchiseProfile profile, FranchiseProfileRequestDTO dto) {
        profile.setAuthorizedPersonName(dto.getAuthorizedPersonName());
        profile.setOfficialEmail(dto.getOfficialEmail());
        profile.setMobileNumber(dto.getMobileNumber());
        profile.setBrandName(dto.getBrandName());
        profile.setWebsite(dto.getWebsite());
        profile.setOpportunityType(FranchiseProfile.OpportunityType.valueOf(dto.getOpportunityType().toUpperCase()));
        profile.setIndustry(dto.getIndustry());
        profile.setAboutBrand(dto.getAboutBrand());
        profile.setProductsServices(dto.getProductsServices());
        profile.setStartOperationsDate(dto.getStartOperationsDate());
        profile.setHeadquartersLocation(dto.getHeadquartersLocation());
        profile.setGlobalOutletsCount(dto.getGlobalOutletsCount());
        profile.setExpectationsFromUser(dto.getExpectationsFromUser());
        profile.setSupportProvided(dto.getSupportProvided());
        profile.setFranchiseProcedure(dto.getFranchiseProcedure());
        profile.setExpansionLocations(dto.getExpansionLocations());
        profile.setPaymentPlan(FranchiseProfile.PaymentPlan.valueOf(dto.getPaymentPlan().toUpperCase()));

        // ✅ Update Franchise Formats safely
        profile.getFranchiseFormats().clear();
        if (dto.getFranchiseFormats() != null) {
            dto.getFranchiseFormats().forEach(formatDto -> {
                FranchiseProfile.FranchiseFormat format = new FranchiseProfile.FranchiseFormat();
                format.setFormatName(formatDto.getFormatName());
                format.setMinSqFt(formatDto.getMinSqFt());
                format.setMaxSqFt(formatDto.getMaxSqFt());
                format.setMinInvestment(formatDto.getMinInvestment());
                format.setMaxInvestment(formatDto.getMaxInvestment());
                format.setCurrency(formatDto.getCurrency());
                format.setBrandFee(formatDto.getBrandFee());
                format.setAverageStaffRequired(formatDto.getAverageStaffRequired());
                format.setRoyaltyDetails(formatDto.getRoyaltyDetails());
                format.setAverageMonthlySales(formatDto.getAverageMonthlySales());
                format.setAverageEBITDA(formatDto.getAverageEBITDA());

                profile.getFranchiseFormats().add(format);
            });
        }
    }

    private FranchiseProfileResponseDTO mapToResponseDTO(FranchiseProfile profile) {
        FranchiseProfileResponseDTO dto = new FranchiseProfileResponseDTO();
        dto.setId(profile.getId());
        dto.setAuthorizedPersonName(profile.getAuthorizedPersonName());
        dto.setOfficialEmail(profile.getOfficialEmail());
        dto.setMobileNumber(profile.getMobileNumber());
        dto.setBrandName(profile.getBrandName());
        dto.setWebsite(profile.getWebsite());
        dto.setOpportunityType(profile.getOpportunityType().name());
        dto.setIndustry(profile.getIndustry());
        dto.setAboutBrand(profile.getAboutBrand());
        dto.setProductsServices(profile.getProductsServices());
        dto.setStartOperationsDate(profile.getStartOperationsDate());
        dto.setHeadquartersLocation(profile.getHeadquartersLocation());
        dto.setGlobalOutletsCount(profile.getGlobalOutletsCount());
        dto.setExpectationsFromUser(profile.getExpectationsFromUser());
        dto.setSupportProvided(profile.getSupportProvided());
        dto.setFranchiseProcedure(profile.getFranchiseProcedure());
        dto.setExpansionLocations(profile.getExpansionLocations());

        dto.setFranchiseFormats(profile.getFranchiseFormats()
                .stream()
                .map(format -> {
                    FranchiseProfileResponseDTO.FranchiseFormatDTO formatDTO = new FranchiseProfileResponseDTO.FranchiseFormatDTO();
                    formatDTO.setFormatName(format.getFormatName());
                    formatDTO.setMinSqFt(format.getMinSqFt());
                    formatDTO.setMaxSqFt(format.getMaxSqFt());
                    formatDTO.setMinInvestment(format.getMinInvestment());
                    formatDTO.setMaxInvestment(format.getMaxInvestment());
                    formatDTO.setCurrency(format.getCurrency());
                    formatDTO.setBrandFee(format.getBrandFee());
                    formatDTO.setAverageStaffRequired(format.getAverageStaffRequired());
                    formatDTO.setRoyaltyDetails(format.getRoyaltyDetails());
                    formatDTO.setAverageMonthlySales(format.getAverageMonthlySales());
                    formatDTO.setAverageEBITDA(format.getAverageEBITDA());
                    return formatDTO;
                }).collect(Collectors.toList()));

        dto.setBrandLogoPath(profile.getBrandLogoPath());
        dto.setBusinessPhotosPaths(profile.getBusinessPhotosPaths());
        dto.setBrochuresDocumentsPaths(profile.getBrochuresDocumentsPaths());
        dto.setProofOfBusinessPath(profile.getProofOfBusinessPath());
        dto.setPaymentPlan(profile.getPaymentPlan().name());

        // ✅ Fetch plan features and add to response
        planFeatureRepository.findByPaymentPlan(profile.getPaymentPlan())
                .ifPresent(planFeature -> dto.setPlanFeatures(planFeature.getFeatures()));

        return dto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<FranchiseProfile> filterFranchiseProfiles(
            FranchiseProfile.OpportunityType opportunityType,
            String industry,
            String headquartersLocation) {

        return repository.filterProfiles(opportunityType, industry, headquartersLocation);
    }
}
