package com.llamatours.claim.service;

import com.llamatours.claim.dto.ClaimResponse;
import com.llamatours.claim.dto.CreateClaimRequest;
import com.llamatours.claim.dto.ReplyClaimRequest;
import com.llamatours.claim.entity.Claim;
import com.llamatours.claim.enums.ClaimStatus;
import com.llamatours.claim.repository.ClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;

    @Transactional
    public ClaimResponse createClaim(CreateClaimRequest request) {
        // Generar código único y secuencial basado en el año actual y la cuenta actual de reclamos
        long count = claimRepository.count();
        String code = String.format("%s-%d-%05d", 
                request.getClaimType().name(), 
                Year.now().getValue(), 
                count + 1);

        var claim = Claim.builder()
                .code(code)
                .claimType(request.getClaimType())
                .fullName(request.getFullName())
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .address(request.getAddress())
                .email(request.getEmail())
                .phone(request.getPhone())
                .minor(request.isMinor())
                .guardianName(request.isMinor() ? request.getGuardianName() : null)
                .guardianDocumentType(request.isMinor() ? request.getGuardianDocumentType() : null)
                .guardianDocumentNumber(request.isMinor() ? request.getGuardianDocumentNumber() : null)
                .goodType(request.getGoodType())
                .claimedAmount(request.getClaimedAmount())
                .goodDescription(request.getGoodDescription())
                .description(request.getDescription())
                .consumerRequest(request.getConsumerRequest())
                .status(ClaimStatus.PENDIENTE)
                .build();

        Claim savedClaim = claimRepository.save(claim);
        return mapToResponse(savedClaim);
    }

    @Transactional(readOnly = true)
    public List<ClaimResponse> getAllClaims() {
        return claimRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ClaimResponse getClaimById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamación no encontrada con ID: " + id));
        return mapToResponse(claim);
    }

    @Transactional
    public ClaimResponse replyToClaim(Long id, ReplyClaimRequest request) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamación no encontrada con ID: " + id));

        claim.setReply(request.getReply());
        claim.setRepliedAt(LocalDateTime.now());
        claim.setStatus(ClaimStatus.ATENDIDO);

        Claim updatedClaim = claimRepository.save(claim);
        return mapToResponse(updatedClaim);
    }

    private ClaimResponse mapToResponse(Claim claim) {
        return ClaimResponse.builder()
                .id(claim.getId())
                .code(claim.getCode())
                .createdAt(claim.getCreatedAt())
                .claimType(claim.getClaimType())
                .fullName(claim.getFullName())
                .documentType(claim.getDocumentType())
                .documentNumber(claim.getDocumentNumber())
                .address(claim.getAddress())
                .email(claim.getEmail())
                .phone(claim.getPhone())
                .minor(claim.isMinor())
                .guardianName(claim.getGuardianName())
                .guardianDocumentType(claim.getGuardianDocumentType())
                .guardianDocumentNumber(claim.getGuardianDocumentNumber())
                .goodType(claim.getGoodType())
                .claimedAmount(claim.getClaimedAmount())
                .goodDescription(claim.getGoodDescription())
                .description(claim.getDescription())
                .consumerRequest(claim.getConsumerRequest())
                .reply(claim.getReply())
                .repliedAt(claim.getRepliedAt())
                .status(claim.getStatus())
                .build();
    }
}
