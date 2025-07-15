package com.abhi.smergersclone.repository;

import com.abhi.smergersclone.entity.MemberProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Long> {

    // Find by official email
    Optional<MemberProfile> findByOfficialEmail(String officialEmail);

    // Check if profile exists by email
    boolean existsByOfficialEmail(String officialEmail);

    // Find by mobile number
    Optional<MemberProfile> findByMobileNumber(String mobileNumber);
}
