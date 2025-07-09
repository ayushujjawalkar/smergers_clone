package com.abhi.smergersclone.dto;
import com.abhi.smergersclone.entity.MemberProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class MemberProfileRequestDTO {

    // Confidential Information
    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "Official email is required")
    private String officialEmail;

    // Requirements
    @NotNull(message = "At least one interest is required")
    private List<MemberProfile.InterestType> interests;

    @NotNull(message = "Member type is required")
    private MemberProfile.MemberType memberType;

    private List<String> interestedIndustries;
    private List<String> interestedLocations;
    private InvestmentRangeDTO investmentRange;
    private String currentLocation;

    // Company Information
    private String designation;
    private String companyWebsiteOrLinkedIn;
    private String companyIndustryActivity;
    private String businessFactors;
    private String aboutCompany;

    // Documents
    private MultipartFile companyLogo;
    private MultipartFile corporateProfile;
    private MultipartFile proofOfBusiness;

    // Plan
    private MemberProfile.MembershipPlan membershipPlan;

    @Data
    public static class InvestmentRangeDTO {
        private String currency = "INR";
        private Double minAmount;
        private Double maxAmount;
    }
}