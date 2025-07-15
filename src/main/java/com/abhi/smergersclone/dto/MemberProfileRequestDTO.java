package com.abhi.smergersclone.dto;

import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberProfileRequestDTO {
    private String fullName;
    private String mobileNumber;
    private String officialEmail;

    private List<String> interests;
    private String memberType;
    private List<String> interestedIndustries;
    private List<String> interestedLocations;

    private String currency;
    private Double minAmount;
    private Double maxAmount;

    private String currentLocation;
    private String timezone;

    private String designation;
    private String companyWebsiteOrLinkedIn;
    private String companyIndustryActivity;
    private String businessFactors;
    private String aboutCompany;

    private String companyLogoPath;
    private String corporateProfilePath;
    private String proofOfBusinessPath;

    private String planType;
    private String activationDate;
    private String expiryDate;
    private Integer remainingCredits;
    private String amountPaid;
}
