//package com.abhi.smergersclone.service;
//import com.abhi.smergersclone.dto.*;
//import com.abhi.smergersclone.entity.MemberProfile;
//import com.abhi.smergersclone.exception.ResourceNotFoundException;
//import com.abhi.smergersclone.mapper.MemberProfileMapper;
//import com.abhi.smergersclone.repository.MemberProfileRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class MemberProfileService {
//
//    private final MemberProfileRepository memberProfileRepository;
//    private final MemberProfileMapper memberProfileMapper;
//    private final FileStorageService fileStorageService;
//
//    // Create a new member profile
//    @Transactional
//    public MemberProfileResponseDTO createMemberProfile(MemberProfileRequestDTO requestDTO) {
//        // Validate unique constraints
//        if (memberProfileRepository.existsByOfficialEmail(requestDTO.getOfficialEmail())) {
//            throw new IllegalArgumentException("Email already registered");
//        }
//
//        if (memberProfileRepository.existsByMobileNumber(requestDTO.getMobileNumber())) {
//            throw new IllegalArgumentException("Mobile number already registered");
//        }
//
//        MemberProfile memberProfile = memberProfileMapper.toEntity(requestDTO);
//        MemberProfile savedProfile = memberProfileRepository.save(memberProfile);
//        return memberProfileMapper.toResponseDTO(savedProfile);
//    }
//
//    // Get member profile by ID
//    @Transactional(readOnly = true)
//    public MemberProfileResponseDTO getMemberProfileById(Long id) {
//        return memberProfileRepository.findById(id)
//                .map(memberProfileMapper::toResponseDTO)
//                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found with id: " + id));
//    }
//
//    // Update member profile
//    @Transactional
//    public MemberProfileResponseDTO updateMemberProfile(Long id, MemberProfileRequestDTO requestDTO) {
//        return memberProfileRepository.findById(id)
//                .map(existingProfile -> {
//                    MemberProfile updatedProfile = memberProfileMapper.toEntity(requestDTO);
//                    updatedProfile.setId(id); // Preserve existing ID
//                    // Preserve audit fields
//                    updatedProfile.setCreatedAt(existingProfile.getCreatedAt());
//                    return memberProfileMapper.toResponseDTO(
//                            memberProfileRepository.save(updatedProfile)
//                    );
//                })
//                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found with id: " + id));
//    }
//
//    // Delete member profile
//    @Transactional
//    public void deleteMemberProfile(Long id) {
//        if (!memberProfileRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Member profile not found with id: " + id);
//        }
//        memberProfileRepository.deleteById(id);
//    }
//
//    // Search member profiles with pagination
//    @Transactional(readOnly = true)
//    public Page<MemberProfileResponseDTO> searchMemberProfiles(
//            String searchQuery,
//            MemberProfile.MemberType memberType,
//            List<MemberProfile.InterestType> interests,
//            List<String> industries,
//            List<String> locations,
//            Double minInvestment,
//            Double maxInvestment,
//            Pageable pageable) {
//
//        Page<MemberProfile> profiles;
//
//        if (searchQuery != null && !searchQuery.isEmpty()) {
//            profiles = memberProfileRepository.searchProfiles(searchQuery, pageable);
//        } else if (memberType != null) {
//            profiles = memberProfileRepository.findByMemberType(memberType, pageable);
//        } else if (interests != null && !interests.isEmpty()) {
//            profiles = memberProfileRepository.findByInterestsIn(interests, pageable);
//        } else if (industries != null && !industries.isEmpty()) {
//            profiles = memberProfileRepository.findByIndustriesIn(industries, pageable);
//        } else if (locations != null && !locations.isEmpty()) {
//            profiles = memberProfileRepository.findByLocationsIn(locations, pageable);
//        } else if (minInvestment != null || maxInvestment != null) {
//            profiles = memberProfileRepository.findByInvestmentRange(minInvestment, maxInvestment, pageable);
//        } else {
//            profiles = memberProfileRepository.findAll(pageable);
//        }
//
//        return profiles.map(memberProfileMapper::toResponseDTO);
//    }
//
//    // Upload company logo
//    @Transactional
//    public MemberProfileResponseDTO uploadCompanyLogo(Long id, MultipartFile file) {
//        MemberProfile profile = memberProfileRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));
//
//        String filePath = fileStorageService.storeFile("member-logos", file);
//        profile.setCompanyLogoPath(filePath);
//
//        return memberProfileMapper.toResponseDTO(
//                memberProfileRepository.save(profile)
//        );
//    }
//
//    // Get dashboard statistics
//    @Transactional(readOnly = true)
//    public MemberProfileStatsDTO getMemberProfileStats() {
//        List<Object[]> stats = memberProfileRepository.countByMemberType();
//
//        MemberProfileStatsDTO statsDTO = new MemberProfileStatsDTO();
//        stats.forEach(stat -> {
//            MemberProfile.MemberType type = (MemberProfile.MemberType) stat[0];
//            Long count = (Long) stat[1];
//
//            switch (type) {
//                case INDIVIDUAL -> statsDTO.setIndividualCount(count);
//                case COMPANY -> statsDTO.setCompanyCount(count);
//                case INVESTOR -> statsDTO.setInvestorCount(count);
//                case LENDER -> statsDTO.setLenderCount(count);
//                case FRANCHISEE -> statsDTO.setFranchiseeCount(count);
//            }
//        });
//
//        return statsDTO;
//    }
//
//    // Upgrade membership plan
//    @Transactional
//    public MemberProfileResponseDTO upgradeMembershipPlan(Long id, MemberProfile.MembershipPlan plan) {
//        MemberProfile profile = memberProfileRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));
//
//        profile.setMembershipPlan(plan);
//
//        // Add introduction credits based on plan
//        if (plan == MemberProfile.MembershipPlan.PREMIUM) {
//            profile.setIntroductionCredits(25); // Premium members get 25 credits
//        } else {
//            profile.setIntroductionCredits(0); // Reset for active plan
//        }
//
//        return memberProfileMapper.toResponseDTO(
//                memberProfileRepository.save(profile)
//        );
//    }
//}

package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.*;
import com.abhi.smergersclone.entity.MemberProfile;
import com.abhi.smergersclone.exception.ResourceNotFoundException;
import com.abhi.smergersclone.mapper.MemberProfileMapper;
import com.abhi.smergersclone.repository.MemberProfileRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Getter @Setter
@Service
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberProfileMapper memberProfileMapper;
    private final FileStorageService fileStorageService;

    // Create a new member profile with FREE plan by default
    @Transactional
    public MemberProfileResponseDTO createMemberProfile(MemberProfileRequestDTO requestDTO) {
        if (memberProfileRepository.existsByOfficialEmail(requestDTO.getOfficialEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (memberProfileRepository.existsByMobileNumber(requestDTO.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        MemberProfile memberProfile = memberProfileMapper.toEntity(requestDTO);
        // Initialize with FREE plan by default
        memberProfile.setMembershipPlanDetails(new MemberProfile.MembershipPlanDetails());
        MemberProfile savedProfile = memberProfileRepository.save(memberProfile);
        return memberProfileMapper.toResponseDTO(savedProfile);
    }

    // Get member profile by ID with plan details
    @Transactional(readOnly = true)
    public MemberProfileResponseDTO getMemberProfileById(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found with id: " + id));

        MemberProfileResponseDTO responseDTO = memberProfileMapper.toResponseDTO(profile);

        // Only include plan details if exists and is active
        if (profile.getMembershipPlanDetails() != null &&
                profile.getMembershipPlanDetails().isPlanActive()) {
            responseDTO.setMembershipPlan(mapPlanDetails(profile.getMembershipPlanDetails()));
        }

        return responseDTO;
    }

    // Update member profile (preserves plan details)
    @Transactional
    public MemberProfileResponseDTO updateMemberProfile(Long id, MemberProfileRequestDTO requestDTO) {
        return memberProfileRepository.findById(id)
                .map(existingProfile -> {
                    MemberProfile updatedProfile = memberProfileMapper.toEntity(requestDTO);
                    updatedProfile.setId(id);
                    // Preserve existing plan details and audit fields
                    updatedProfile.setMembershipPlanDetails(existingProfile.getMembershipPlanDetails());
                    updatedProfile.setCreatedAt(existingProfile.getCreatedAt());
                    return memberProfileMapper.toResponseDTO(
                            memberProfileRepository.save(updatedProfile)
                    );
                })
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found with id: " + id));
    }

    // Delete member profile
    @Transactional
    public void deleteMemberProfile(Long id) {
        if (!memberProfileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member profile not found with id: " + id);
        }
        memberProfileRepository.deleteById(id);
    }

    // Plan Management Methods
    @Transactional
    public MemberProfileResponseDTO upgradeToPremiumPlan(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        MemberProfile.MembershipPlanDetails planDetails = new MemberProfile.MembershipPlanDetails();
        planDetails.setPlanType(MemberProfile.MembershipPlanType.PREMIUM);
        planDetails.setActivationDate(LocalDate.now());
        planDetails.setExpiryDate(LocalDate.now().plusYears(3));
        planDetails.setRemainingCredits(25);
        planDetails.setAmountPaid(new BigDecimal("9999"));
        planDetails.setBenefits(getPremiumBenefits());

        profile.setMembershipPlanDetails(planDetails);
        return memberProfileMapper.toResponseDTO(memberProfileRepository.save(profile));
    }

    @Transactional
    public MemberProfileResponseDTO upgradeToActivePlan(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        MemberProfile.MembershipPlanDetails planDetails = new MemberProfile.MembershipPlanDetails();
        planDetails.setPlanType(MemberProfile.MembershipPlanType.ACTIVE);
        planDetails.setActivationDate(LocalDate.now());
        planDetails.setExpiryDate(LocalDate.now().plusYears(3));
        planDetails.setRemainingCredits(10);
        planDetails.setAmountPaid(new BigDecimal("7999"));
        planDetails.setBenefits(getActiveBenefits());

        profile.setMembershipPlanDetails(planDetails);
        return memberProfileMapper.toResponseDTO(memberProfileRepository.save(profile));
    }

    @Transactional
    public MemberProfileResponseDTO useIntroductionCredit(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        if (profile.getMembershipPlanDetails() == null ||
                !profile.getMembershipPlanDetails().isPlanActive()) {
            throw new IllegalStateException("No active plan found");
        }

        if (!profile.getMembershipPlanDetails().hasAvailableCredits()) {
            throw new IllegalStateException("No credits remaining");
        }

        profile.getMembershipPlanDetails().setRemainingCredits(
                profile.getMembershipPlanDetails().getRemainingCredits() - 1);

        return memberProfileMapper.toResponseDTO(memberProfileRepository.save(profile));
    }

    // Helper methods
    private MemberProfileResponseDTO.MembershipPlanDetailsDTO mapPlanDetails(
            MemberProfile.MembershipPlanDetails details) {
        MemberProfileResponseDTO.MembershipPlanDetailsDTO dto =
                new MemberProfileResponseDTO.MembershipPlanDetailsDTO();
        dto.setPlanName(details.getPlanType().name());
        dto.setStatus(details.isPlanActive() ? "Active" : "Expired");
        dto.setActivationDate(details.getActivationDate());
        dto.setExpiryDate(details.getExpiryDate());
        dto.setRemainingCredits(details.getRemainingCredits());
        dto.setBenefits(parseBenefits(details.getBenefits()));
        return dto;
    }

    private List<String> parseBenefits(String benefitsJson) {
        // Implement JSON parsing logic
        // Example: return objectMapper.readValue(benefitsJson, List.class);
        return List.of(benefitsJson.split(",")); // Simplified for example
    }

    private String getPremiumBenefits() {
        return String.join(",",
                "Speedy profile activation within 1 business day",
                "Profile marked as 'Premium' with higher visibility",
                "25 introduction credits",
                "Access to full business metrics",
                "Priority email support"
        );
    }

    private String getActiveBenefits() {
        return String.join(",",
                "Speedy profile activation within 1 business day",
                "Profile visibility boost",
                "10 introduction credits",
                "Access to basic business metrics",
                "Standard email support"
        );
    }

    // ... (keep existing search, file upload, and stats methods) ...
    public Page<MemberProfileResponseDTO> searchMemberProfiles(
            String query,
            MemberProfile.MemberType memberType,
            List<MemberProfile.InterestType> interests,
            List<String> industries,
            List<String> locations,
            Double minInvestment,
            Double maxInvestment,
            Pageable pageable) {

        // Convert parameters as needed and delegate to repository
        Page<MemberProfile> profiles = memberProfileRepository.searchProfiles(
                query,
                memberType,
                interests,
                industries,
                locations,
                minInvestment,
                maxInvestment,
                pageable);

        return profiles.map(memberProfileMapper::toResponseDTO);
    }


    public MemberProfileResponseDTO uploadCompanyLogo(Long id, MultipartFile file) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        // Upload file and get the path
        String filePath = fileStorageService.storeFile(file);

        // Update profile with new logo path
        profile.setCompanyLogoPath(filePath);
        MemberProfile updatedProfile = memberProfileRepository.save(profile);

        return memberProfileMapper.toResponseDTO(updatedProfile);
    }

    public MemberProfileStatsDTO getMemberProfileStats() {
        // Create new stats object
        MemberProfileStatsDTO stats = new MemberProfileStatsDTO();

        // Get all counts from database
        List<Object[]> counts = memberProfileRepository.countByMemberType();

        // Initialize all counts to 0
        stats.setIndividualCount(0L);
        stats.setCompanyCount(0L);
        stats.setInvestorCount(0L);
        stats.setLenderCount(0L);
        stats.setFranchiseeCount(0L);

        // Fill in the counts we got from database
        for (Object[] count : counts) {
            MemberProfile.MemberType type = (MemberProfile.MemberType) count[0];
            Long quantity = (Long) count[1];

            switch (type) {
                case INDIVIDUAL:
                    stats.setIndividualCount(quantity);
                    break;
                case COMPANY:
                    stats.setCompanyCount(quantity);
                    break;
                case INVESTOR:
                    stats.setInvestorCount(quantity);
                    break;
                case LENDER:
                    stats.setLenderCount(quantity);
                    break;
                case FRANCHISEE:
                    stats.setFranchiseeCount(quantity);
                    break;
            }
        }

        // totalCount is automatically calculated by getTotalCount()
        return stats;
    }
    private Map<String, Long> getMemberTypeCounts() {
        return memberProfileRepository.countByMemberType().stream()
                .collect(Collectors.toMap(
                        arr -> ((MemberProfile.MemberType) arr[0]).name(),
                        arr -> (Long) arr[1]
                ));
    }

    private Long countActivePremiumMembers() {
        return memberProfileRepository.countByMembershipPlanDetails_PlanTypeAndExpiryDateGreaterThanEqual(
                MemberProfile.MembershipPlanType.PREMIUM,
                LocalDate.now()
        );
    }

    private Long countActivePlans() {
        return memberProfileRepository.countByMembershipPlanDetails_ExpiryDateGreaterThanEqual(
                LocalDate.now()
        );
    }



    public MemberProfileResponseDTO upgradeMembershipPlan(Long id, MemberProfile.MembershipPlanType planType) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        MemberProfile.MembershipPlanDetails planDetails = new MemberProfile.MembershipPlanDetails();
        planDetails.setPlanType(planType);
        planDetails.setActivationDate(LocalDate.now());

        // Set different values based on plan type
        switch (planType) {
            case PREMIUM:
                planDetails.setExpiryDate(LocalDate.now().plusYears(1));
                planDetails.setRemainingCredits(25);
                planDetails.setAmountPaid(new BigDecimal("9999"));
                break;
            case ACTIVE:
                planDetails.setExpiryDate(LocalDate.now().plusYears(1));
                planDetails.setRemainingCredits(10);
                planDetails.setAmountPaid(new BigDecimal("4999"));
                break;
            case FREE:
            default:
                planDetails.setExpiryDate(null);
                planDetails.setRemainingCredits(0);
                planDetails.setAmountPaid(BigDecimal.ZERO);
                break;
        }

        profile.setMembershipPlanDetails(planDetails);
        MemberProfile updatedProfile = memberProfileRepository.save(profile);
        return memberProfileMapper.toResponseDTO(updatedProfile);
    }
}
