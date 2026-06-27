package com.llamatours.booking.dto;

import com.llamatours.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {

    private Long id;
    private Integer peopleCount;
    private BookingStatus status;
    private Long userId;
    private Long expeditionId;
    private String expeditionName;
    private Long availabilityId;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Double totalAmount;
    private String paymentId;
    private String receiptNumber;
}

