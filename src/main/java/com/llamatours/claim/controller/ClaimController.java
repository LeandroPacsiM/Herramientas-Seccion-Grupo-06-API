package com.llamatours.claim.controller;

import com.llamatours.claim.dto.ClaimResponse;
import com.llamatours.claim.dto.CreateClaimRequest;
import com.llamatours.claim.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    @PostMapping
    public ResponseEntity<ClaimResponse> createClaim(@Valid @RequestBody CreateClaimRequest request) {
        ClaimResponse response = claimService.createClaim(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
