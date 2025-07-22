package com.abhi.smergersclone.repository;

import com.abhi.smergersclone.entity.MemberProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    // Find by official email
    Optional<MemberProfile> findByOfficialEmail(String officialEmail);

    // Check if profile exists by email
    boolean existsByOfficialEmail(String officialEmail);

    // Find by mobile number
    Optional<MemberProfile> findByMobileNumber(String mobileNumber);
    // ✅ Fetch profiles that contain a specific interest
    Page<MemberProfile> findByInterestsContaining(MemberProfile.InterestType interestType, Pageable pageable);

     //filter investor by memberType (investor type)
    Page<MemberProfile> findByMemberType(MemberProfile.MemberType memberType, Pageable pageable);

    // Filter by currentLocation
    List<MemberProfile> findByCurrentLocationIgnoreCase(String location);

    // Filter by interestedLocations (ElementCollection)
    @Query("SELECT m FROM MemberProfile m JOIN m.interestedLocations l WHERE LOWER(l) = LOWER(:location)")
    List<MemberProfile> findByInterestedLocation(@Param("location") String location);

    // filter by industries
    @Query("SELECT m FROM MemberProfile m JOIN m.interestedIndustries i WHERE LOWER(i) = LOWER(:industry)")
    List<MemberProfile> findByInterestedIndustry(@Param("industry") String industry);

}
