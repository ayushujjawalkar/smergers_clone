package com.abhi.smergersclone.controller;

import com.abhi.smergersclone.dto.BusinessListingRequest;
import com.abhi.smergersclone.dto.BusinessListingResponse;
import com.abhi.smergersclone.service.BusinessListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class BusinessListingController {

    private final BusinessListingService listingService;

    @PostMapping
    public ResponseEntity<BusinessListingResponse> createListing(@RequestBody BusinessListingRequest request) {
        return ResponseEntity.ok(listingService.createListing(request));
    }

    @GetMapping
    public ResponseEntity<List<BusinessListingResponse>> getAllListings() {
        return ResponseEntity.ok(listingService.getAllListings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessListingResponse> getListingById(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.getListingById(id));
    }

    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteListing(@PathVariable Long id) {
//        listingService.deleteListing(id);
//        return ResponseEntity.noContent().build();
//    }
    public ResponseEntity<Map<String, Object>> deleteListing(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.deleteListing(id));
    }

}
