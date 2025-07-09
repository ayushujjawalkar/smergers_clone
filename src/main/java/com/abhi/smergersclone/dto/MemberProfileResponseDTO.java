package com.abhi.smergersclone.dto;
import com.abhi.smergersclone.entity.MemberProfile;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class MemberProfileResponseDTO {

    private Long id;

    // Confidential Information
    private String fullName;
    private String mobileNumber;
    private String officialEmail;

    // Requirements
    private List<MemberProfile.InterestType> interests;
    private MemberProfile.MemberType memberType;
    private List<String> interestedIndustries;
    private List<String> interestedLocations;
    private InvestmentRangeDTO investmentRange;
    private String currentLocation;
    private String timezone;

    // Company Information
    private String designation;
    private String companyWebsiteOrLinkedIn;
    private String companyIndustryActivity;
    private String businessFactors;
    private String aboutCompany;

    // Documents
    private String companyLogoPath;
    private String corporateProfilePath;
    private String proofOfBusinessPath;

    // Plan
    private MemberProfile.MembershipPlan membershipPlan;
    private Integer introductionCredits;

    // Audit
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class InvestmentRangeDTO {
        private String currency;
        private Double minAmount;
        private Double maxAmount;
    }
}