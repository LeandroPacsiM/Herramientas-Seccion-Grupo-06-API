package com.llamatours.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsResponse {
    private Long bookingId;
    private String receiptNumber;
    private String expeditionName;
    private String startDate;
    private String endDate;
    private Integer peopleCount;
    private Double totalAmount;
    private String status;
    private String paymentId;
    private String bookingDate;
    private String customerName;
    private String customerEmail;
}
