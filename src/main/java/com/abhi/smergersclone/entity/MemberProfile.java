package com.abhi.smergersclone.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "member_profiles")
@Data
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ========== Confidential Information Section ==========
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String officialEmail;

    // ========== Requirements Section ==========
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InterestType> interests;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @ElementCollection
    private List<String> interestedIndustries;

    @ElementCollection
    private List<String> interestedLocations;

    @Embedded
    private InvestmentRange investmentRange;

    private String currentLocation;
    private String timezone = "Asia/Kolkata";

    // ========== Company Information Section ==========
    private String designation;
    private String companyWebsiteOrLinkedIn;
    private String companyIndustryActivity;

    @Column(length = 2000)
    private String businessFactors;

    @Column(length = 2000)
    private String aboutCompany;

    // ========== Documents & Proof Section ==========
    private String companyLogoPath;
    private String corporateProfilePath;
    private String proofOfBusinessPath;

    // ========== Plan Section ==========
    @Enumerated(EnumType.STRING)
    private MembershipPlan membershipPlan = MembershipPlan.ACTIVE;

    private Integer introductionCredits = 0;

    // ========== Audit Fields ==========
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ========== Enums ==========
    public enum InterestType {
        ACQUIRING_BUSINESS,
        INVESTING_IN_BUSINESS,
        LENDING_TO_BUSINESS,
        BUYING_ASSETS,
        FRANCHISE_DISTRIBUTORSHIP
    }

    public enum MemberType {
        INDIVIDUAL,
        COMPANY,
        INVESTOR,
        LENDER,
        FRANCHISEE
    }

    public enum MembershipPlan {
        ACTIVE,
        PREMIUM
    }

    // ========== Embedded Classes ==========
    @Embeddable
    @Data
    public static class InvestmentRange {
        private String currency = "INR";
        private Double minAmount;
        private Double maxAmount;
    }
}