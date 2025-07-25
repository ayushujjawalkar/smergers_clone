package com.abhi.smergersclone.BUSINESS_PROFILE;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BusinessProfileRequestDTO {
    private Long userId;
    private String name;
    private String companyName;
    private String email;
    private String mobile;
    private String businessType;
    private LocalDate businessEstablished;
    private String industry;
    private String location;
    private Integer employeesCount;
    private String legalEntityType;
    private String description;
    private String productsServices;
    private String highlights;
    private String facilityDetails;
    private String fundingDetails;
    private BigDecimal monthlySales;
    private BigDecimal yearlySales;
    private BigDecimal profitMargin;
    private String assetsDetails;
    private BigDecimal physicalAssetsValue;
    private String planType;
    private String status;
}