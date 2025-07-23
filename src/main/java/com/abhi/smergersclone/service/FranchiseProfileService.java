package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;
import com.abhi.smergersclone.entity.FranchiseProfile;

import java.util.List;

public interface FranchiseProfileService {

    FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO);

    FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO);

    FranchiseProfileResponseDTO getFranchiseProfileById(Long id);

    List<FranchiseProfileResponseDTO> getAllFranchiseProfiles();

    void deleteFranchiseProfile(Long id);

    // ✅ Filter method
    List<FranchiseProfile> filterFranchiseProfiles(
            FranchiseProfile.OpportunityType opportunityType,
            String industry,
            String headquartersLocation);

         //for count clicks
        void incrementClick(Long profileId);
    List<FranchiseProfile> filterByInvestmentRange(Double min, Double max);
}
