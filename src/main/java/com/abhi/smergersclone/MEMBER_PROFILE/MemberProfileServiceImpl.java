package com.abhi.smergersclone.MEMBER_PROFILE;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberProfileServiceImpl implements MemberProfileService {

    private final MemberProfileRepository memberProfileRepository;

    @Override
    public MemberProfileResponseDTO createProfile(MemberProfileRequestDTO requestDTO) {
        if (memberProfileRepository.existsByOfficialEmail(requestDTO.getOfficialEmail())) {
            throw new RuntimeException("Profile already exists with this email.");
        }

        MemberProfile entity = MemberProfileMapper.mapToEntity(requestDTO);
        MemberProfile savedEntity = memberProfileRepository.save(entity);
        return MemberProfileMapper.mapToResponse(savedEntity);
    }

    @Override
    public MemberProfileResponseDTO updateProfile(Long id, MemberProfileRequestDTO requestDTO) {
        MemberProfile existing = memberProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with ID: " + id));

        // Update fields from request DTO
        MemberProfile updatedEntity = MemberProfileMapper.mapToEntity(requestDTO);
        updatedEntity.setId(existing.getId()); // Preserve ID
        updatedEntity.setCreatedAt(existing.getCreatedAt()); // Preserve created date

        MemberProfile savedEntity = memberProfileRepository.save(updatedEntity);
        return MemberProfileMapper.mapToResponse(savedEntity);
    }

    @Override
    public MemberProfileResponseDTO getProfile(Long id) {
        MemberProfile entity = memberProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found with ID: " + id));
        return MemberProfileMapper.mapToResponse(entity);
    }

    @Override
    public Page<MemberProfileResponseDTO> getAllProfiles(Pageable pageable) {
        return memberProfileRepository.findAll(pageable)
                .map(MemberProfileMapper::mapToResponse);
    }

    @Override
    public MemberProfileResponseDTO activatePlan(Long memberId, String planType, String amountPaid) {
        MemberProfile entity = memberProfileRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Profile not found with ID: " + memberId));

        MemberProfile.MembershipPlanType selectedPlan =
                MemberProfile.MembershipPlanType.valueOf(planType.toUpperCase());

        MemberProfile.MembershipPlanDetails details = MemberProfile.MembershipPlanDetails.builder()
                .planType(selectedPlan)
                .activationDate(LocalDate.now())
                .expiryDate(LocalDate.now().plusMonths(selectedPlan.getValidityInMonths()))
                .remainingCredits(selectedPlan.getCredits())
                .amountPaid(new BigDecimal(amountPaid))
                .build();

        entity.setMembershipPlanDetails(details);
        memberProfileRepository.save(entity);

        return MemberProfileMapper.mapToResponse(entity);
    }

    @Override
    public Page<MemberProfileResponseDTO> getProfilesByInterest(MemberProfile.InterestType interestType, Pageable pageable) {
        Page<MemberProfile> profiles = memberProfileRepository.findByInterestsContaining(interestType, pageable);

        return profiles.map(MemberProfileMapper::mapToResponse);
    }

    @Override
    public Page<MemberProfileResponseDTO> getProfilesByMemberType(MemberProfile.MemberType memberType, Pageable pageable) {
        Page<MemberProfile> profiles = memberProfileRepository.findByMemberType(memberType, pageable);
        return profiles.map(MemberProfileMapper::mapToResponse);
    }

    @Override
    public List<MemberProfileResponseDTO> filterByLocation(String location, boolean searchInInterestedLocations) {
        List<MemberProfile> profiles;

        if (searchInInterestedLocations) {
            profiles = memberProfileRepository.findByInterestedLocation(location);
        } else {
            profiles = memberProfileRepository.findByCurrentLocationIgnoreCase(location);
        }

        return profiles.stream()
                .map(MemberProfileMapper::mapToResponse)
                .toList();
    }


    @Override
    public List<MemberProfileResponseDTO> filterByIndustry(String industry) {
        List<MemberProfile> profiles = memberProfileRepository.findByInterestedIndustry(industry);
        return profiles.stream()
                .map(MemberProfileMapper::mapToResponse)
                .toList();
    }
    @Transactional
    @Override
    public void incrementClick(Long profileId) {
        memberProfileRepository.incrementClickCount(profileId);
    }
    @Override
    public MemberProfile verifyProfile(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member Profile not found"));
        profile.setVerified(true);
        return memberProfileRepository.save(profile);
    }

    @Override
    public MemberProfile unverifyProfile(Long id) {
        MemberProfile profile = memberProfileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member Profile not found"));
        profile.setVerified(false);
        return memberProfileRepository.save(profile);
    }

}
