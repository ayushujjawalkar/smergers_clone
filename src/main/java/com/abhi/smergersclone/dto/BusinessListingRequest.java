package com.abhi.smergersclone.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessListingRequest {

    private String title;
    private String description;
    private String industry;
    private String country;
    private String city;
    private Double valuation;
    private Double revenue;
    private String businessType;
}
