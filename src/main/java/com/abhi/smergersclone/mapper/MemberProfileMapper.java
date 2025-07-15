//package com.abhi.smergersclone.mapper;
//import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
//import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
//import com.abhi.smergersclone.entity.MemberProfile;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//
//@Mapper(componentModel = "spring")
//public interface MemberProfileMapper {
//
//    MemberProfileMapper INSTANCE = Mappers.getMapper(MemberProfileMapper.class);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "createdAt", ignore = true)
//    @Mapping(target = "updatedAt", ignore = true)
//    @Mapping(target = "companyLogoPath", ignore = true)
//    @Mapping(target = "corporateProfilePath", ignore = true)
//    @Mapping(target = "proofOfBusinessPath", ignore = true)
//    @Mapping(target = "introductionCredits", ignore = true)
//    MemberProfile toEntity(MemberProfileRequestDTO dto);
//
//    MemberProfileResponseDTO toResponseDTO(MemberProfile entity);
//
//    @Mapping(target = "id", ignore = true)
//    void updateEntityFromDTO(MemberProfileRequestDTO dto, @MappingTarget MemberProfile entity);
//
//
//    @Mapping(target = "membershipPlan", source = "membershipPlanDetails")
//
//    @Mapping(target = "planName", source = "planType")
//    @Mapping(target = "status", expression = "java(details.isPlanActive() ? \"Active\" : \"Expired\")")
//    MemberProfileResponseDTO.MembershipPlanDetailsDTO mapPlanDetails(
//            MemberProfile.MembershipPlanDetails details);
//}
package com.abhi.smergersclone.mapper;

import com.abhi.smergersclone.dto.MemberProfileRequestDTO;
import com.abhi.smergersclone.dto.MemberProfileResponseDTO;
import com.abhi.smergersclone.entity.MemberProfile;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.util.StringUtils;
import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring",
        imports = {Arrays.class, StringUtils.class})
public interface MemberProfileMapper {

    MemberProfileMapper INSTANCE = Mappers.getMapper(MemberProfileMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "companyLogoPath", ignore = true)
    @Mapping(target = "corporateProfilePath", ignore = true)
    @Mapping(target = "proofOfBusinessPath", ignore = true)
    @Mapping(target = "membershipPlanDetails", ignore = true) // Handled separately
    MemberProfile toEntity(MemberProfileRequestDTO dto);

    @Mapping(target = "membershipPlan", source = "membershipPlanDetails",
            conditionExpression = "java(entity.getMembershipPlanDetails() != null && " +
                    "entity.getMembershipPlanDetails().isPlanActive())",
            qualifiedByName = "mapPlanDetails") // Add qualifiedByName
    MemberProfileResponseDTO toResponseDTO(MemberProfile entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "membershipPlanDetails", ignore = true) // Don't update plan details via regular update
    void updateEntityFromDTO(MemberProfileRequestDTO dto, @MappingTarget MemberProfile entity);

    @AfterMapping
    default void setDefaultPlanDetails(@MappingTarget MemberProfile memberProfile) {
        if (memberProfile.getMembershipPlanDetails() == null) {
            MemberProfile.MembershipPlanDetails details = new MemberProfile.MembershipPlanDetails();
            details.setPlanType(MemberProfile.MembershipPlanType.FREE);
            memberProfile.setMembershipPlanDetails(details);
        }
    }

    @Named("mapPlanDetails")
    default MemberProfileResponseDTO.MembershipPlanDetailsDTO mapPlanDetails(
            MemberProfile.MembershipPlanDetails details) {
        if (details == null) {
            return null;
        }

        MemberProfileResponseDTO.MembershipPlanDetailsDTO dto =
                new MemberProfileResponseDTO.MembershipPlanDetailsDTO();
        dto.setPlanName(getPlanDisplayName(details.getPlanType()));
        dto.setStatus(details.isPlanActive() ? "Active" : "Expired");
        dto.setActivationDate(details.getActivationDate());
        dto.setExpiryDate(details.getExpiryDate());
        dto.setRemainingCredits(details.getRemainingCredits());
        dto.setAmountPaid(details.getAmountPaid());
        dto.setBenefits(parseBenefits(details.getBenefits())); // Use the named method here

        return dto;
    }




    @Named("parseBenefits")
    default List<String> parseBenefits(String benefits) {
        if (!StringUtils.hasText(benefits)) {
            return List.of();
        }
        return Arrays.asList(benefits.split(";"));
    }

    @Named("getPlanDisplayName")
    default String getPlanDisplayName(MemberProfile.MembershipPlanType planType) {
        if (planType == null) return "Free";
        return switch (planType) {
            case FREE -> "Free Plan";
            case ACTIVE -> "Active Plan";
            case PREMIUM -> "Premium Plan";
        };
    }
}