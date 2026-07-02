package com.llamatours.claim.dto;

import com.llamatours.claim.enums.ClaimStatus;
import com.llamatours.claim.enums.ClaimType;
import com.llamatours.claim.enums.DocumentType;
import com.llamatours.claim.enums.GoodType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimResponse {
    private Long id;
    private String code;
    private LocalDateTime createdAt;
    private ClaimType claimType;
    private String fullName;
    private DocumentType documentType;
    private String documentNumber;
    private String address;
    private String email;
    private String phone;
    private boolean minor;
    private String guardianName;
    private DocumentType guardianDocumentType;
    private String guardianDocumentNumber;
    private GoodType goodType;
    private BigDecimal claimedAmount;
    private String goodDescription;
    private String description;
    private String consumerRequest;
    private String reply;
    private LocalDateTime repliedAt;
    private ClaimStatus status;
}
