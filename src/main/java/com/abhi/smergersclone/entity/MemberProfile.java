//package com.abhi.smergersclone.entity;
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Entity
//@Table(name = "member_profiles")
//@Data
//public class MemberProfile {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // ========== Confidential Information Section ==========
//    @Column(nullable = false)
//    private String fullName;
//
//    @Column(nullable = false)
//    private String mobileNumber;
//
//    @Column(nullable = false)
//    private String officialEmail;
//
//    // ========== Requirements Section ==========
//    @ElementCollection
//    @Enumerated(EnumType.STRING)
//    private List<InterestType> interests;
//
//    @Enumerated(EnumType.STRING)
//    private MemberType memberType;
//
//    @ElementCollection
//    private List<String> interestedIndustries;
//
//    @ElementCollection
//    private List<String> interestedLocations;
//
//    @Embedded
//    private InvestmentRange investmentRange;
//
//
//    private String currentLocation;
//    private String timezone = "Asia/Kolkata";
//
//    // ========== Company Information Section ==========
//    private String designation;
//    private String companyWebsiteOrLinkedIn;
//    private String companyIndustryActivity;
//
//    @Column(length = 2000)
//    private String businessFactors;
//
//    @Column(length = 2000)
//    private String aboutCompany;
//
//    // ========== Documents & Proof Section ==========
//    private String companyLogoPath;
//    private String corporateProfilePath;
//    private String proofOfBusinessPath;
//
//    // ========== Plan Section ==========
//    @Enumerated(EnumType.STRING)
//    private MembershipPlan membershipPlan = MembershipPlan.ACTIVE;
//
//    private Integer introductionCredits = 0;
//
//    // ========== Audit Fields ==========
//    @CreationTimestamp
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    private LocalDateTime updatedAt;
//
//    // ========== Enums ==========
//    public enum InterestType {
//        ACQUIRING_BUSINESS,
//        INVESTING_IN_BUSINESS,
//        LENDING_TO_BUSINESS,
//        BUYING_ASSETS,
//        FRANCHISE_DISTRIBUTORSHIP
//    }
//
//    public enum MemberType {
//        INDIVIDUAL,
//        COMPANY,
//        INVESTOR,
//        LENDER,
//        FRANCHISEE
//    }
//
//    public enum MembershipPlan {
//        ACTIVE,
//        PREMIUM
//    }
//
//    // ========== Embedded Classes ==========
//    @Embeddable
//    @Data
//    public static class InvestmentRange {
//        private String currency = "INR";
//        private Double minAmount;
//        private Double maxAmount;
//    }
//}
package com.abhi.smergersclone.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@Builder
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
    @Builder.Default
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

    // ========== Enhanced Plan Section ==========
    @Embedded
    private MembershipPlanDetails membershipPlanDetails;

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

    public enum MembershipPlanType {
        FREE,
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

    @Embeddable
    @Data
    public static class MembershipPlanDetails {
        @Enumerated(EnumType.STRING)
        private MembershipPlanType planType = MembershipPlanType.FREE;

        private LocalDate activationDate;
        private LocalDate expiryDate;
        private Integer remainingCredits = 0;
        private BigDecimal amountPaid = BigDecimal.ZERO;

        @Column(length = 1000)
        private String benefits; // JSON-formatted benefits

        public boolean isPlanActive() {
            return expiryDate != null && !LocalDate.now().isAfter(expiryDate);
        }

        public boolean hasAvailableCredits() {
            return remainingCredits != null && remainingCredits > 0;
        }
    }

    // Helper method to check premium features access
    public boolean hasPremiumAccess() {
        return membershipPlanDetails != null &&
                membershipPlanDetails.getPlanType() == MembershipPlanType.PREMIUM &&
                membershipPlanDetails.isPlanActive();
    }
}
