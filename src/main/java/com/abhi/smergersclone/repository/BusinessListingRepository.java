package com.abhi.smergersclone.repository;

import com.abhi.smergersclone.entity.BusinessListing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessListingRepository extends JpaRepository<BusinessListing, Long> {
}
