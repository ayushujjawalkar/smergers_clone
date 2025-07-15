package com.abhi.smergersclone.repository;


import com.abhi.smergersclone.entity.FranchiseProfile;
import com.abhi.smergersclone.entity.PlanFeature;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanFeatureRepository extends JpaRepository<PlanFeature, Long> {
    Optional<PlanFeature> findByPaymentPlan(FranchiseProfile.PaymentPlan paymentPlan);
}
