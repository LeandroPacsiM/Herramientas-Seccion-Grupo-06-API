package com.llamatours.claim.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReplyClaimRequest {
    @NotBlank
    private String reply;
}
