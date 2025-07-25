package com.abhi.smergersclone.MEMBER_PROFILE;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class MemberProfileMapper {

    public static MemberProfile mapToEntity(MemberProfileRequestDTO dto) {
        MemberProfile entity = new MemberProfile();
        entity.setFullName(dto.getFullName());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setOfficialEmail(dto.getOfficialEmail());

        if (dto.getInterests() != null) {
            entity.setInterests(dto.getInterests().stream()
                    .map(i -> MemberProfile.InterestType.valueOf(i.toUpperCase()))
                    .collect(Collectors.toList()));
        }

        if (dto.getMemberType() != null) {
            entity.setMemberType(MemberProfile.MemberType.valueOf(dto.getMemberType().toUpperCase()));
        }

        entity.setInterestedIndustries(dto.getInterestedIndustries());
        entity.setInterestedLocations(dto.getInterestedLocations());

        entity.setInvestmentRange(MemberProfile.InvestmentRange.builder()
                .currency(dto.getCurrency() != null ? dto.getCurrency() : "INR")
                .minAmount(dto.getMinAmount())
                .maxAmount(dto.getMaxAmount())
                .build());



        entity.setCurrentLocation(dto.getCurrentLocation());
        entity.setTimezone(dto.getTimezone());

        entity.setDesignation(dto.getDesignation());
        entity.setCompanyWebsiteOrLinkedIn(dto.getCompanyWebsiteOrLinkedIn());
        entity.setCompanyIndustryActivity(dto.getCompanyIndustryActivity());
        entity.setBusinessFactors(dto.getBusinessFactors());
        entity.setAboutCompany(dto.getAboutCompany());

        entity.setCompanyLogoPath(dto.getCompanyLogoPath());
        entity.setCorporateProfilePath(dto.getCorporateProfilePath());
        entity.setProofOfBusinessPath(dto.getProofOfBusinessPath());

        if (dto.getPlanType() != null) {
            MemberProfile.MembershipPlanType planType =
                    MemberProfile.MembershipPlanType.valueOf(dto.getPlanType().toUpperCase());

            MemberProfile.MembershipPlanDetails details = new MemberProfile.MembershipPlanDetails();
            details.setPlanType(planType);
            details.setActivationDate(LocalDate.now());
            details.setExpiryDate(LocalDate.now().plusMonths(planType.getValidityInMonths()));
            details.setRemainingCredits(planType.getCredits());

            entity.setMembershipPlanDetails(details);
        }

        return entity;
    }

    public static MemberProfileResponseDTO mapToResponse(MemberProfile entity) {
        return MemberProfileResponseDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .mobileNumber(entity.getMobileNumber())
                .officialEmail(entity.getOfficialEmail())
                .interests(entity.getInterests() != null
                        ? entity.getInterests().stream().map(Enum::name).collect(Collectors.toList())
                        : null)
                .memberType(entity.getMemberType() != null ? entity.getMemberType().name() : null)
                .interestedIndustries(entity.getInterestedIndustries())
                .interestedLocations(entity.getInterestedLocations())
                .currency(entity.getInvestmentRange() != null ? entity.getInvestmentRange().getCurrency() : null)
                .minAmount(entity.getInvestmentRange() != null ? entity.getInvestmentRange().getMinAmount() : null)
                .maxAmount(entity.getInvestmentRange() != null ? entity.getInvestmentRange().getMaxAmount() : null)
                .currentLocation(entity.getCurrentLocation())
                .timezone(entity.getTimezone())
                .designation(entity.getDesignation())
                .companyWebsiteOrLinkedIn(entity.getCompanyWebsiteOrLinkedIn())
                .companyIndustryActivity(entity.getCompanyIndustryActivity())
                .businessFactors(entity.getBusinessFactors())
                .aboutCompany(entity.getAboutCompany())
                .companyLogoPath(entity.getCompanyLogoPath())
                .corporateProfilePath(entity.getCorporateProfilePath())
                .proofOfBusinessPath(entity.getProofOfBusinessPath())
                .planType(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getPlanType().name()
                        : null)
                .activationDate(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getActivationDate().toString()
                        : null)
                .expiryDate(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getExpiryDate().toString()
                        : null)
                .remainingCredits(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getRemainingCredits()
                        : null)
                .amountPaid(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getAmountPaid().toString()
                        : null)
                .benefits(entity.getMembershipPlanDetails() != null
                        ? entity.getMembershipPlanDetails().getPlanType().getBenefits()
                        : null)
                .premiumAccess(entity.hasPremiumAccess())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }



}
