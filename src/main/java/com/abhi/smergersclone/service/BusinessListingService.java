package com.abhi.smergersclone.service;

import com.abhi.smergersclone.dto.BusinessListingRequest;
import com.abhi.smergersclone.dto.BusinessListingResponse;
import com.abhi.smergersclone.entity.BusinessListing;
import com.abhi.smergersclone.entity.User;
import com.abhi.smergersclone.repository.BusinessListingRepository;
import com.abhi.smergersclone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BusinessListingService {

    private final BusinessListingRepository listingRepository;
    private final UserRepository userRepository;

    public BusinessListingResponse createListing(BusinessListingRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        BusinessListing listing = BusinessListing.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .industry(request.getIndustry())
                .country(request.getCountry())
                .city(request.getCity())
                .valuation(request.getValuation())
                .revenue(request.getRevenue())
                .businessType(request.getBusinessType())
                .createdAt(LocalDateTime.now())
                .owner(user)
                .build();

        listingRepository.save(listing);

        return mapToResponse(listing);
    }

    public List<BusinessListingResponse> getAllListings() {
        return listingRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BusinessListingResponse getListingById(Long id) {
        BusinessListing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found"));
        return mapToResponse(listing);
    }

//    public void deleteListing(Long id) {
//        BusinessListing listing = listingRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Listing not found"));
//
//        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (!listing.getOwner().getEmail().equals(currentUser)) {
//            throw new RuntimeException("You are not authorized to delete this listing");
//        }
//
//        listingRepository.delete(listing);
//    }
public Map<String, Object> deleteListing(Long id) {
    BusinessListing listing = listingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Listing not found"));

    String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!listing.getOwner().getEmail().equals(currentUser)) {
        throw new RuntimeException("You are not authorized to delete this listing");
    }

    listingRepository.delete(listing);

    // Prepare response
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Listing deleted successfully");
    response.put("listing", mapToResponse(listing));
    return response;
}

    private BusinessListingResponse mapToResponse(BusinessListing listing) {
        return BusinessListingResponse.builder()
                .id(listing.getId())
                .title(listing.getTitle())
                .industry(listing.getIndustry())
                .country(listing.getCountry())
                .valuation(listing.getValuation())
                .businessType(listing.getBusinessType())
                .ownerEmail(listing.getOwner().getEmail())
                .build();
    }
}
