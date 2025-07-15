package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import com.abhi.smergersclone.entity.MemberProfile;
import com.abhi.smergersclone.mapper.MemberProfileMapper;
import com.abhi.smergersclone.repository.MemberProfileRepository;
import com.abhi.smergersclone.service.MemberProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}
