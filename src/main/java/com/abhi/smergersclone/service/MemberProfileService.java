package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import com.abhi.smergersclone.entity.MemberProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberProfileService {

    MemberProfileResponseDTO createProfile(MemberProfileRequestDTO requestDTO);

    MemberProfileResponseDTO updateProfile(Long id, MemberProfileRequestDTO requestDTO);

    MemberProfileResponseDTO getProfile(Long id);

    Page<MemberProfileResponseDTO> getAllProfiles(Pageable pageable);

    MemberProfileResponseDTO activatePlan(Long memberId, String planType, String amountPaid);

    Page<MemberProfileResponseDTO> getProfilesByInterest(MemberProfile.InterestType interestType, Pageable pageable);

    Page<MemberProfileResponseDTO> getProfilesByMemberType(MemberProfile.MemberType memberType, Pageable pageable);

    List<MemberProfileResponseDTO> filterByLocation(String location, boolean searchInInterestedLocations);

    List<MemberProfileResponseDTO> filterByIndustry(String industry);

    void incrementClick(Long profileId);

    MemberProfile verifyProfile(Long id);
    MemberProfile unverifyProfile(Long id);

}

