package com.abhi.smergersclone.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "franchise_profiles")
@Data
public class FranchiseProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========== Confidential Information Section ==========
    private String authorizedPersonName;
    private String officialEmail;
    private String mobileNumber;

    // ========== Brand Details Section ==========
    private String brandName;
    private String website;

    //it id for count clicks
    private Long clickCount = 0L; // Default 0


    @Enumerated(EnumType.STRING)
    private OpportunityType opportunityType;

    private String industry;

    @Column(length = 2000)
    private String aboutBrand;

    @Column(length = 2000)
    private String productsServices;

    @Temporal(TemporalType.DATE)
    private Date startOperationsDate;

    private String headquartersLocation;
    private Integer globalOutletsCount;

    @Column(length = 2000)
    private String expectationsFromUser;

    @Column(length = 2000)
    private String supportProvided;

    @Column(length = 2000)
    private String franchiseProcedure;

    @Column(length = 1000)
    private String expansionLocations;

    // ========== Franchise Formats Section ==========
    @ElementCollection
    @CollectionTable(name = "franchise_formats", joinColumns = @JoinColumn(name = "profile_id"))
    private List<FranchiseFormat> franchiseFormats;

    // ========== Proof Documents Section ==========
    private String brandLogoPath;

    @ElementCollection
    @CollectionTable(name = "business_photos", joinColumns = @JoinColumn(name = "profile_id"))
    private List<String> businessPhotosPaths;

    @ElementCollection
    @CollectionTable(name = "brochures_documents", joinColumns = @JoinColumn(name = "profile_id"))
    private List<String> brochuresDocumentsPaths;

    private String proofOfBusinessPath;

    // ========== Payment Plan Section ==========
    @Enumerated(EnumType.STRING)
    private PaymentPlan paymentPlan;

    // ========== Enums ==========
    public enum OpportunityType {
        FRANCHISE_OPPORTUNITY,
        DEALERSHIP_OPPORTUNITY,
        RESELLER_OPPORTUNITY,
        DISTRIBUTOR_OPPORTUNITY,
        SALES_PARTNER_OPPORTUNITY
    }

    public enum PaymentPlan {
        PREMIUM_FRANCHISE,
        PROFESSIONAL_FRANCHISE
    }

    // ========== Embedded Franchise Format ==========
    @Embeddable
    @Data
    public static class FranchiseFormat {
        private String formatName;
        private Integer minSqFt;
        private Integer maxSqFt;
        private Double minInvestment;
        private Double maxInvestment;
        private String currency;
        private Double brandFee;
        private Integer averageStaffRequired;

        @Column(length = 1000)
        private String royaltyDetails;

        private Double averageMonthlySales;
        private Double averageEBITDA;
    }
}
