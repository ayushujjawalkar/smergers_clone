package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberProfileService {

    MemberProfileResponseDTO createProfile(MemberProfileRequestDTO requestDTO);

    MemberProfileResponseDTO updateProfile(Long id, MemberProfileRequestDTO requestDTO);

    MemberProfileResponseDTO getProfile(Long id);

    Page<MemberProfileResponseDTO> getAllProfiles(Pageable pageable);

    MemberProfileResponseDTO activatePlan(Long memberId, String planType, String amountPaid);
}

