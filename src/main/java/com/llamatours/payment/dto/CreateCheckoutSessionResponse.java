package com.llamatours.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCheckoutSessionResponse {
    private String sessionUrl;
    private String sessionId;
}
