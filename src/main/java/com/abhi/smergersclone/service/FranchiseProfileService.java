package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.FranchiseProfileRequestDTO;
import com.abhi.smergersclone.dto.FranchiseProfileResponseDTO;

import java.util.List;

public interface FranchiseProfileService {

    FranchiseProfileResponseDTO createFranchiseProfile(FranchiseProfileRequestDTO requestDTO);

    FranchiseProfileResponseDTO updateFranchiseProfile(Long id, FranchiseProfileRequestDTO requestDTO);

    FranchiseProfileResponseDTO getFranchiseProfileById(Long id);

    List<FranchiseProfileResponseDTO> getAllFranchiseProfiles();

    void deleteFranchiseProfile(Long id);
}
