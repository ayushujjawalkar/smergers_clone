package com.abhi.smergersclone.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "business_profiles")
public class BusinessProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String mobile;

    private String businessType;
    private LocalDate businessEstablished;
    private String industry;
    private String location;
    private Integer employeesCount;
    private String legalEntityType;

    @Column(length = 2000)
    private String description;

    @Column(length = 2000)
    private String productsServices;

    @Column(length = 2000)
    private String highlights;

    @Column(length = 1000)
    private String facilityDetails;

    @Column(length = 1000)
    private String fundingDetails;

    private BigDecimal monthlySales;
    private BigDecimal yearlySales;
    private BigDecimal profitMargin;

    @Column(length = 1000)
    private String assetsDetails;

    private BigDecimal physicalAssetsValue;
    private String planType;
    private String status = "DRAFT";

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Document> documents = new HashSet<>();
}


