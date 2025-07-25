package com.abhi.smergersclone.PLAN_TYPE;
import com.abhi.smergersclone.FRANCHISE_PROFILE.FranchiseProfile;
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
