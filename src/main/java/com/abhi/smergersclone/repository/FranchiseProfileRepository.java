package com.abhi.smergersclone.repository;
import com.abhi.smergersclone.entity.FranchiseProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    @Query("SELECT f FROM FranchiseProfile f " +
            "WHERE (:opportunityType IS NULL OR f.opportunityType = :opportunityType) " +
            "AND (:industry IS NULL OR LOWER(f.industry) = LOWER(:industry)) " +
            "AND (:headquartersLocation IS NULL OR LOWER(f.headquartersLocation) = LOWER(:headquartersLocation))")
    List<FranchiseProfile> filterProfiles(
            @Param("opportunityType") FranchiseProfile.OpportunityType opportunityType,
            @Param("industry") String industry,
            @Param("headquartersLocation") String headquartersLocation);
    //for count clicks
    @Modifying
    @Query("UPDATE FranchiseProfile f SET f.clickCount = f.clickCount + 1 WHERE f.id = :id")
    void incrementClickCount(@Param("id") Long id);

    //for filter investment range
    @Query("SELECT f FROM FranchiseProfile f WHERE " +
            "(f.investmentRangeMin >= :min AND f.investmentRangeMax <= :max)")
    List<FranchiseProfile> findByInvestmentRange(@Param("min") Double min, @Param("max") Double max);

    List<FranchiseProfile> findByVerifiedTrue(); // ✅ To get only verified profiles

    @Query("SELECT f FROM FranchiseProfile f WHERE LOWER(f.brandName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<FranchiseProfile> searchFranchiseProfiles(@Param("keyword") String keyword);

}
