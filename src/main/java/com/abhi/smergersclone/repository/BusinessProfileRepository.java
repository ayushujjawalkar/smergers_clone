package com.abhi.smergersclone.repository;

import com.abhi.smergersclone.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BusinessProfileRepository extends JpaRepository<BusinessProfile, Long> {

    @Query("SELECT b FROM BusinessProfile b " +
            "WHERE (:businessType IS NULL OR LOWER(b.businessType) = LOWER(:businessType)) " +
            "AND (:industry IS NULL OR LOWER(b.industry) = LOWER(:industry)) " +
            "AND (:location IS NULL OR LOWER(b.location) = LOWER(:location))")
    List<BusinessProfile> filterProfiles(
            @Param("businessType") String businessType,
            @Param("industry") String industry,
            @Param("location") String location);

    @Modifying
    @Query("UPDATE BusinessProfile b SET b.clickCount = b.clickCount + 1 WHERE b.id = :id")
    void incrementClickCount(@Param("id") Long id);

    //for apply filter on investment range  of business list
    @Query("SELECT b FROM BusinessProfile b WHERE b.investmentRequired BETWEEN :minInvestment AND :maxInvestment")
    List<BusinessProfile> findByInvestmentRange(@Param("minInvestment") Double minInvestment,
                                                @Param("maxInvestment") Double maxInvestment);


//find by verified profile
    List<BusinessProfile> findByVerifiedTrue(); // For verified profiles only

}