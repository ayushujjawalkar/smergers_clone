package com.abhi.smergersclone.dto;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class FranchiseProfileRequestDTO {

    // Confidential Information
    private String authorizedPersonName;
    private String officialEmail;
    private String mobileNumber;

    // Brand Details
    private String brandName;
    private String website;
    private String opportunityType;  // Enum as String
    private String industry;
    private String aboutBrand;
    private String productsServices;
    private Date startOperationsDate;
    private String headquartersLocation;
    private Integer globalOutletsCount;
    private String expectationsFromUser;
    private String supportProvided;
    private String franchiseProcedure;
    private String expansionLocations;

    // Franchise Formats
    private List<FranchiseFormatDTO> franchiseFormats;

    // Payment Plan
    private String paymentPlan;  // Enum as String

    @Data
    public static class FranchiseFormatDTO {
        private String formatName;
        private Integer minSqFt;
        private Integer maxSqFt;
        private Double minInvestment;
        private Double maxInvestment;
        private String currency;
        private Double brandFee;
        private Integer averageStaffRequired;
        private String royaltyDetails;
        private Double averageMonthlySales;
        private Double averageEBITDA;
    }
}
