package com.abhi.smergersclone.dto;
import com.abhi.smergersclone.entity.FranchiseProfile;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class FranchiseProfileResponseDTO {
    private Long id;

    // ========== Confidential Information Section ==========
    private String authorizedPersonName;
    private String officialEmail;
    private String mobileNumber;

    // ========== Brand Details Section ==========
    private String brandName;
    private String website;
    private FranchiseProfile.OpportunityType opportunityType;
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

    // ========== Franchise Formats Section ==========
    private List<FranchiseFormatDTO> franchiseFormats;

    // ========== Proof Documents Section ==========
    private String brandLogoPath;
    private List<String> businessPhotosPaths;
    private List<String> brochuresDocumentsPaths;
    private String proofOfBusinessPath;

    // ========== Payment Plan Section ==========
    private FranchiseProfile.PaymentPlan paymentPlan;
    private Date createdAt;
    private Date updatedAt;

    @Data
    public static class FranchiseFormatDTO {
        private Long id;
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