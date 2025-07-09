package com.abhi.smergersclone.service;
import com.abhi.smergersclone.dto.*;
import com.abhi.smergersclone.entity.MemberProfile;
import com.abhi.smergersclone.exception.ResourceNotFoundException;
import com.abhi.smergersclone.mapper.MemberProfileMapper;
import com.abhi.smergersclone.repository.MemberProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;
    private final MemberProfileMapper memberProfileMapper;
    private final FileStorageService fileStorageService;

    // Create a new member profile
    @Transactional
    public MemberProfileResponseDTO createMemberProfile(MemberProfileRequestDTO requestDTO) {
        // Validate unique constraints
        if (memberProfileRepository.existsByOfficialEmail(requestDTO.getOfficialEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        if (memberProfileRepository.existsByMobileNumber(requestDTO.getMobileNumber())) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        MemberProfile memberProfile = memberProfileMapper.toEntity(requestDTO);
        MemberProfile savedProfile = memberProfileRepository.save(memberProfile);
        return memberProfileMapper.toResponseDTO(savedProfile);
    }

    // Get member profile by ID
    @Transactional(readOnly = true)
    public MemberProfileResponseDTO getMemberProfileById(Long id) {
        return memberProfileRepository.findById(id)
                .map(memberProfileMapper::toResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found with id: " + id));
    }

    // Update member profile
    @Transactional
    public MemberProfileResponseDTO updateMemberProfile(Long id, MemberProfileRequestDTO requestDTO) {
        return memberProfileRepository.findById(id)
                .map(existingProfile -> {
                    MemberProfile updatedProfile = memberProfileMapper.toEntity(requestDTO);
                    updatedProfile.setId(id); // Preserve existing ID
                    // Preserve audit fields
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

    // Search member profiles with pagination
    @Transactional(readOnly = true)
    public Page<MemberProfileResponseDTO> searchMemberProfiles(
            String searchQuery,
            MemberProfile.MemberType memberType,
            List<MemberProfile.InterestType> interests,
            List<String> industries,
            List<String> locations,
            Double minInvestment,
            Double maxInvestment,
            Pageable pageable) {

        Page<MemberProfile> profiles;

        if (searchQuery != null && !searchQuery.isEmpty()) {
            profiles = memberProfileRepository.searchProfiles(searchQuery, pageable);
        } else if (memberType != null) {
            profiles = memberProfileRepository.findByMemberType(memberType, pageable);
        } else if (interests != null && !interests.isEmpty()) {
            profiles = memberProfileRepository.findByInterestsIn(interests, pageable);
        } else if (industries != null && !industries.isEmpty()) {
            profiles = memberProfileRepository.findByIndustriesIn(industries, pageable);
        } else if (locations != null && !locations.isEmpty()) {
            profiles = memberProfileRepository.findByLocationsIn(locations, pageable);
        } else if (minInvestment != null || maxInvestment != null) {
            profiles = memberProfileRepository.findByInvestmentRange(minInvestment, maxInvestment, pageable);
        } else {
            profiles = memberProfileRepository.findAll(pageable);
        }

        return profiles.map(memberProfileMapper::toResponseDTO);
    }

    // Upload company logo
    @Transactional
    public MemberProfileResponseDTO uploadCompanyLogo(Long id, MultipartFile file) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        String filePath = fileStorageService.storeFile("member-logos", file);
        profile.setCompanyLogoPath(filePath);

        return memberProfileMapper.toResponseDTO(
                memberProfileRepository.save(profile)
        );
    }

    // Get dashboard statistics
    @Transactional(readOnly = true)
    public MemberProfileStatsDTO getMemberProfileStats() {
        List<Object[]> stats = memberProfileRepository.countByMemberType();

        MemberProfileStatsDTO statsDTO = new MemberProfileStatsDTO();
        stats.forEach(stat -> {
            MemberProfile.MemberType type = (MemberProfile.MemberType) stat[0];
            Long count = (Long) stat[1];

            switch (type) {
                case INDIVIDUAL -> statsDTO.setIndividualCount(count);
                case COMPANY -> statsDTO.setCompanyCount(count);
                case INVESTOR -> statsDTO.setInvestorCount(count);
                case LENDER -> statsDTO.setLenderCount(count);
                case FRANCHISEE -> statsDTO.setFranchiseeCount(count);
            }
        });

        return statsDTO;
    }

    // Upgrade membership plan
    @Transactional
    public MemberProfileResponseDTO upgradeMembershipPlan(Long id, MemberProfile.MembershipPlan plan) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member profile not found"));

        profile.setMembershipPlan(plan);

        // Add introduction credits based on plan
        if (plan == MemberProfile.MembershipPlan.PREMIUM) {
            profile.setIntroductionCredits(25); // Premium members get 25 credits
        } else {
            profile.setIntroductionCredits(0); // Reset for active plan
        }

        return memberProfileMapper.toResponseDTO(
                memberProfileRepository.save(profile)
        );
    }
}
