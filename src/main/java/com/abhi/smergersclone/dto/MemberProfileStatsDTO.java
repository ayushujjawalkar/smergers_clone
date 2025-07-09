package com.abhi.smergersclone.dto;
import lombok.Data;

@Data
public class MemberProfileStatsDTO {
    private Long individualCount;
    private Long companyCount;
    private Long investorCount;
    private Long lenderCount;
    private Long franchiseeCount;
    private Long totalCount;

    public Long getTotalCount() {
        return individualCount + companyCount + investorCount + lenderCount + franchiseeCount;
    }
}