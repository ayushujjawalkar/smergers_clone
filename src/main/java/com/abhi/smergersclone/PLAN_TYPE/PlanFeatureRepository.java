package com.abhi.smergersclone.PLAN_TYPE;


import com.abhi.smergersclone.FRANCHISE_PROFILE.FranchiseProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanFeatureRepository extends JpaRepository<PlanFeature, Long> {
    Optional<PlanFeature> findByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan);
}
