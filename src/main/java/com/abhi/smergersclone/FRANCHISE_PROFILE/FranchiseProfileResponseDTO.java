package com.abhi.smergersclone.FRANCHISE_PROFILE;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class FranchiseProfileResponseDTO {

    private Long id;

    // Confidential Information
    private String authorizedPersonName;
    private String officialEmail;
    private String mobileNumber;

    private String planFeatures; // Detailed benefits of the selected plan


    // Brand Details
    private String brandName;
    private String website;
    private String opportunityType;
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

    // Proof Documents
    private String brandLogoPath;
    private List<String> businessPhotosPaths;
    private List<String> brochuresDocumentsPaths;
    private String proofOfBusinessPath;

    // Payment Plan
    private String paymentPlan;

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
