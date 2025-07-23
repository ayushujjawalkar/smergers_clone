package com.abhi.smergersclone.controller;

import com.abhi.smergersclone.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
// GET /api/search?type=business&keyword=restaurant
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public ResponseEntity<Object> search(
            @RequestParam String type,
            @RequestParam String keyword) {
        return ResponseEntity.ok(searchService.searchProfiles(type, keyword));
    }
}
