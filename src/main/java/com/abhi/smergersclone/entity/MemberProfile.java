package com.abhi.smergersclone.entity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "member_profiles")
public class MemberProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Confidential Information
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String officialEmail;

    // Requirements
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<InterestType> interests;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @ElementCollection
    @CollectionTable(name = "member_interested_industries", joinColumns = @JoinColumn(name = "member_id"))
    private List<String> interestedIndustries;

    @ElementCollection
    @CollectionTable(name = "member_interested_locations", joinColumns = @JoinColumn(name = "member_id"))
    private List<String> interestedLocations;

    @Embedded
    private InvestmentRange investmentRange;

    private String currentLocation;

    @Builder.Default
    private String timezone = "Asia/Kolkata";

    // Company Info
    private String designation;
    private String companyWebsiteOrLinkedIn;
    private String companyIndustryActivity;

    @Column(length = 2000)
    private String businessFactors;

    @Column(length = 2000)
    private String aboutCompany;

    // Documents
    private String companyLogoPath;
    private String corporateProfilePath;
    private String proofOfBusinessPath;

    // Membership Details
    @Embedded
    private MembershipPlanDetails membershipPlanDetails;

    // Audit Fields
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // Enums
    public enum InterestType {
        ACQUIRING_BUSINESS,
        INVESTING_IN_BUSINESS,
        LENDING_TO_BUSINESS,
        BUYING_ASSETS,
        FRANCHISE_DISTRIBUTORSHIP
    }

    public enum MemberType {
        INDIVIDUAL, COMPANY, INVESTOR, LENDER, FRANCHISEE
    }

    public enum MembershipPlanType {
        ACTIVE("Active Plan", 10, 36, List.of(
                "Speedy profile activation within 1 business day",
                "Profile marked as 'Premium' and gets higher visibility",
                "10 introduction credits to connect with businesses and franchises",
                "Connect with businesses which send you a proposal for free",
                "Access to metrics of all businesses to evaluate the opportunity",
                "Connect and instantly access Business Name, if business has allowed",
                "Connect and instantly access Contact Details, if business has allowed",
                "Quick email support for your queries",
                "Plan valid till introduction credits are exhausted or for 3 years, whichever is earlier"
        )),
        PREMIUM("Premium Plan", 25, 36, List.of(
                "Speedy profile activation within 1 business day",
                "Profile marked as 'Premium' and gets higher visibility",
                "25 introduction credits to connect with businesses and franchises",
                "Connect with businesses which send you a proposal for free",
                "Access to metrics of all businesses to evaluate the opportunity",
                "Connect and instantly access Business Name, if business has allowed",
                "Connect and instantly access Contact Details, if business has allowed",
                "Quick email support for your queries",
                "Plan valid till introduction credits are exhausted or for 3 years, whichever is earlier"
        )),
        YEARLY("Yearly Plan", -1, 12, List.of(
                "Speedy profile activation within 1 business day",
                "Profile marked as 'Premium' and gets higher visibility",
                "Unlimited introduction credits to connect with businesses and franchises subject to FUP",
                "Connect with businesses which send you a proposal for free",
                "Access to metrics of all businesses to evaluate the opportunity",
                "Connect and instantly access Business Name, if business has allowed",
                "Connect and instantly access Contact Details, if business has allowed",
                "Quick email support for your queries",
                "Valid for a period of 1 year"
        ));


        private final String displayName;
        private final int credits;
        private final int validityInMonths;
        private final List<String> benefits;

        MembershipPlanType(String displayName, int credits, int validityInMonths, List<String> benefits) {
            this.displayName = displayName;
            this.credits = credits;
            this.validityInMonths = validityInMonths;
            this.benefits = benefits;
        }

        public String getDisplayName() { return displayName; }
        public int getCredits() { return credits; }
        public int getValidityInMonths() { return validityInMonths; }
        public List<String> getBenefits() { return benefits; }
    }

    // Embedded Classes
    @Builder
    @Embeddable
    @Data
    @NoArgsConstructor // Add this
    @AllArgsConstructor // Add this if you want constructor with all fields
    public static class InvestmentRange {
        @Builder.Default
        private String currency = "INR";
        private Double minAmount;
        private Double maxAmount;
    }


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Embeddable
    @Data
    public static class MembershipPlanDetails {
        @Enumerated(EnumType.STRING)
        private MembershipPlanType planType = MembershipPlanType.ACTIVE;
        private LocalDate activationDate;
        private LocalDate expiryDate;
        private Integer remainingCredits;
        private BigDecimal amountPaid = BigDecimal.ZERO;

        public boolean isPlanActive() {
            return expiryDate != null && !LocalDate.now().isAfter(expiryDate);
        }
    }

    public boolean hasPremiumAccess() {
        return membershipPlanDetails != null &&
                membershipPlanDetails.isPlanActive();
    }
}