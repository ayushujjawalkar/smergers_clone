package com.abhi.smergersclone.repository;

import com.abhi.smergersclone.entity.BusinessProfile;
import org.springframework.data.jpa.repository.JpaRepository;
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
}