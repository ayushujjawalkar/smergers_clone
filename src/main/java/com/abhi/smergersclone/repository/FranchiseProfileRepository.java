package com.abhi.smergersclone.repository;
import com.abhi.smergersclone.entity.FranchiseProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FranchiseProfileRepository extends
        JpaRepository<FranchiseProfile, Long>,
        FranchiseProfileCustomRepository
{

    // Derived Query Methods
    List<FranchiseProfile> findByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan);

    List<FranchiseProfile> findByIndustry(String industry);

    // Custom JPQL Query
    @Query("SELECT fp FROM FranchiseProfile fp WHERE " +
            "fp.globalOutletsCount >= :minOutlets AND " +
            "fp.industry = :industry")
    List<FranchiseProfile> findEstablishedInIndustry(
            @Param("minOutlets") int minOutlets,
            @Param("industry") String industry);

    // Native SQL Query
    @Query(value = "SELECT * FROM franchise_profiles fp " +
            "WHERE fp.headquarters_location = :location " +
            "ORDER BY fp.brand_name",
            nativeQuery = true)
    List<FranchiseProfile> findByHeadquarters(@Param("location") String location);

    //new
    // Custom search method
    @Query("SELECT fp FROM FranchiseProfile fp WHERE " +
            "(:brandName IS NULL OR fp.brandName LIKE %:brandName%) AND " +
            "(:industry IS NULL OR fp.industry = :industry) AND " +
            "(:opportunityType IS NULL OR fp.opportunityType = :opportunityType)")
    List<FranchiseProfile> findByBrandNameContainingAndIndustryAndOpportunityType(
            @Param("brandName") String brandName,
            @Param("industry") String industry,
            @Param("opportunityType") FranchiseProfile.OpportunityType opportunityType);
}