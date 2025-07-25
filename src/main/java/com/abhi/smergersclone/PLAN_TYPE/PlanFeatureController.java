package com.abhi.smergersclone.PLAN_TYPE;

import com.abhi.smergersclone.FRANCHISE_PROFILE.FranchiseProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/plans")
public class PlanFeatureController {

    private final PlanFeatureService planFeatureService;

    public PlanFeatureController(PlanFeatureService planFeatureService) {
        this.planFeatureService = planFeatureService;
    }

    @GetMapping("/{plan}")
    public String getPlanFeatures(@PathVariable("plan") FranchiseProfile.PaymentPlan plan) {
        return planFeatureService.getFeaturesForPlan(plan);
    }
}
