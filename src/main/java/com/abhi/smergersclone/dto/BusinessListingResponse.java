package com.abhi.smergersclone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessListingResponse {

    private Long id;
    private String title;
    private String industry;
    private String country;
    private Double valuation;
    private String businessType;
    private String ownerEmail;
}
