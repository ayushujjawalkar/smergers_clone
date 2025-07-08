package com.abhi.smergersclone.dto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BusinessProfileResponseDTO {
    private Long id;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<DocumentDTO> documents;

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class DocumentDTO {
        private Long id;
        private String documentType;
        private String filePath;
        private LocalDateTime uploadedAt;
    }
}