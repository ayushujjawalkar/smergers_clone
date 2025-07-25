package com.abhi.smergersclone.SEARCH_MAIN;

import com.abhi.smergersclone.BUSINESS_PROFILE.BusinessProfileRepository;
import com.abhi.smergersclone.FRANCHISE_PROFILE.FranchiseProfileRepository;
import com.abhi.smergersclone.MEMBER_PROFILE.MemberProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private BusinessProfileRepository businessRepo;
    @Autowired private FranchiseProfileRepository franchiseRepo;
    @Autowired private MemberProfileRepository memberRepo;

    public Object searchProfiles(String type, String keyword) {
        switch (type.toLowerCase()) {
            case "business":
                return businessRepo.searchBusinessProfiles(keyword);
            case "franchise":
                return franchiseRepo.searchFranchiseProfiles(keyword);
            case "member":
                return memberRepo.searchMemberProfiles(keyword);
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}
