package com.abhi.smergersclone.repository;
import com.abhi.smergersclone.entity.FranchiseProfile;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FranchiseProfileCustomRepository {
    List<FranchiseProfile> findByOpportunityType(FranchiseProfile.OpportunityType type);

    @Query("SELECT fp FROM FranchiseProfile fp WHERE fp.brandName LIKE %:brandName%")
    List<FranchiseProfile> searchByBrandName(@Param("brandName") String brandName);

    @Query("SELECT fp FROM FranchiseProfile fp JOIN fp.franchiseFormats ff WHERE ff.minInvestment <= :investment")
    List<FranchiseProfile> findByMaxInvestment(@Param("investment") Double maxInvestment);
}