package com.llamatours.claim.controller;

import com.llamatours.claim.dto.ClaimResponse;
import com.llamatours.claim.dto.ReplyClaimRequest;
import com.llamatours.claim.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/claims")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminClaimController {

    private final ClaimService claimService;

    @GetMapping
    public ResponseEntity<List<ClaimResponse>> getAllClaims() {
        return ResponseEntity.ok(claimService.getAllClaims());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaimResponse> getClaimById(@PathVariable Long id) {
        return ResponseEntity.ok(claimService.getClaimById(id));
    }

    @PostMapping("/{id}/reply")
    public ResponseEntity<ClaimResponse> replyToClaim(
            @PathVariable Long id,
            @Valid @RequestBody ReplyClaimRequest request) {
        return ResponseEntity.ok(claimService.replyToClaim(id, request));
    }
}
