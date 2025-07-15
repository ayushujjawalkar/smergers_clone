package com.abhi.smergersclone.repository;
import com.abhi.smergersclone.entity.FranchiseProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FranchiseProfileRepository extends JpaRepository<FranchiseProfile, Long> {

    // Find by Brand Name
    List<FranchiseProfile> findByBrandNameContainingIgnoreCase(String brandName);

    // Find by Industry
    List<FranchiseProfile> findByIndustryContainingIgnoreCase(String industry);

    // Find by Opportunity Type (Franchise, Dealership, etc.)
    List<FranchiseProfile> findByOpportunityType(FranchiseProfile.OpportunityType opportunityType);

    // Find by Payment Plan (Premium, Professional)
    List<FranchiseProfile> findByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan);

    // Find by Headquarters Location
    List<FranchiseProfile> findByHeadquartersLocationContainingIgnoreCase(String location);

    // Combine Filters (Industry + PaymentPlan)
    List<FranchiseProfile> findByIndustryAndPaymentPlan(String industry, FranchiseProfile.PaymentPlan paymentPlan);
}
