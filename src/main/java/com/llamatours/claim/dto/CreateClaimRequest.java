package com.llamatours.claim.dto;

import com.llamatours.claim.enums.ClaimType;
import com.llamatours.claim.enums.DocumentType;
import com.llamatours.claim.enums.GoodType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateClaimRequest {

    @NotNull
    private ClaimType claimType;

    @NotBlank
    private String fullName;

    @NotNull
    private DocumentType documentType;

    @NotBlank
    private String documentNumber;

    @NotBlank
    private String address;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    private boolean minor;

    private String guardianName;

    private DocumentType guardianDocumentType;

    private String guardianDocumentNumber;

    @NotNull
    private GoodType goodType;

    private BigDecimal claimedAmount;

    @NotBlank
    private String goodDescription;

    @NotBlank
    private String description;

    @NotBlank
    private String consumerRequest;
}
