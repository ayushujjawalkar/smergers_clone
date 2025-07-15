package com.abhi.smergersclone.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "plan_features")
@Data
public class PlanFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private FranchiseProfile.PaymentPlan paymentPlan; // PREMIUM_FRANCHISE, PROFESSIONAL_FRANCHISE

    @Column(length = 5000)
    private String features; // All plan features as a single text or JSON
}
