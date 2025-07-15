package com.abhi.smergersclone.service;
import com.abhi.smergersclone.entity.FranchiseProfile;
import com.abhi.smergersclone.entity.PlanFeature;
import com.abhi.smergersclone.repository.PlanFeatureRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlanFeatureService {

    private final PlanFeatureRepository planFeatureRepository;

    public PlanFeatureService(PlanFeatureRepository planFeatureRepository) {
        this.planFeatureRepository = planFeatureRepository;
    }

    public String getFeaturesForPlan(FranchiseProfile.PaymentPlan plan) {
        Optional<PlanFeature> planFeature = planFeatureRepository.findByPaymentPlan(plan);
        return planFeature.map(PlanFeature::getFeatures)
                .orElse("No features found for this plan.");
    }
}
