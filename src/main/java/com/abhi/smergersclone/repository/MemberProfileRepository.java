package com.abhi.smergersclone.repository;
import com.abhi.smergersclone.entity.MemberProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    // Basic Query Methods
    boolean existsByOfficialEmail(String email);
    boolean existsByMobileNumber(String mobileNumber);

    // Search Methods
    @Query("SELECT mp FROM MemberProfile mp WHERE " +
            "(:query IS NULL OR " +
            "LOWER(mp.fullName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(mp.designation) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(mp.companyIndustryActivity) LIKE LOWER(CONCAT('%', :query, '%'))) AND " +
            "(:memberType IS NULL OR mp.memberType = :memberType) AND " +
            "(:interests IS NULL OR EXISTS (SELECT i FROM mp.interests i WHERE i IN :interests)) AND " +
            "(:industries IS NULL OR EXISTS (SELECT ind FROM mp.interestedIndustries ind WHERE ind IN :industries)) AND " +
            "(:locations IS NULL OR EXISTS (SELECT loc FROM mp.interestedLocations loc WHERE loc IN :locations)) AND " +
            "(:minInvestment IS NULL OR mp.investmentRange.minAmount >= :minInvestment) AND " +
            "(:maxInvestment IS NULL OR mp.investmentRange.maxAmount <= :maxInvestment)")
    Page<MemberProfile> searchProfiles(
            @Param("query") String query,
            @Param("memberType") MemberProfile.MemberType memberType,
            @Param("interests") List<MemberProfile.InterestType> interests,
            @Param("industries") List<String> industries,
            @Param("locations") List<String> locations,
            @Param("minInvestment") Double minInvestment,
            @Param("maxInvestment") Double maxInvestment,
            Pageable pageable);
    // Filter by Member Type
    Page<MemberProfile> findByMemberType(MemberProfile.MemberType memberType, Pageable pageable);

    // Filter by Interests
    @Query("SELECT mp FROM MemberProfile mp JOIN mp.interests i WHERE i IN :interests")
    Page<MemberProfile> findByInterestsIn(
            @Param("interests") List<MemberProfile.InterestType> interests,
            Pageable pageable);

    // Filter by Industry
    @Query("SELECT DISTINCT mp FROM MemberProfile mp JOIN mp.interestedIndustries ind WHERE ind IN :industries")
    Page<MemberProfile> findByIndustriesIn(
            @Param("industries") List<String> industries,
            Pageable pageable);

    // Filter by Location
    @Query("SELECT DISTINCT mp FROM MemberProfile mp JOIN mp.interestedLocations loc WHERE loc IN :locations")
    Page<MemberProfile> findByLocationsIn(
            @Param("locations") List<String> locations,
            Pageable pageable);

    // Filter by Investment Range
    @Query("SELECT mp FROM MemberProfile mp WHERE " +
            "(:minAmount IS NULL OR mp.investmentRange.minAmount >= :minAmount) AND " +
            "(:maxAmount IS NULL OR mp.investmentRange.maxAmount <= :maxAmount)")
    Page<MemberProfile> findByInvestmentRange(
            @Param("minAmount") Double minAmount,
            @Param("maxAmount") Double maxAmount,
            Pageable pageable);

    // Find Premium Members
    // Correct - should use MembershipPlanDetails
    Page<MemberProfile> findByMembershipPlanDetails_PlanType(
            MemberProfile.MembershipPlanType planType,
            Pageable pageable);

    // Custom Query for Dashboard Stats
    @Query("SELECT mp.memberType, COUNT(mp) FROM MemberProfile mp GROUP BY mp.memberType")
    List<Object[]> countByMemberType();



    //new
    // In MemberProfileRepository
    @Query("SELECT mp.memberType, COUNT(mp) FROM MemberProfile mp GROUP BY mp.memberType")
    long countByMembershipPlanDetails_PlanTypeAndExpiryDateGreaterThanEqual(
            MemberProfile.MembershipPlanType planType,
            LocalDate date);

    long countByMembershipPlanDetails_ExpiryDateGreaterThanEqual(LocalDate date);
}