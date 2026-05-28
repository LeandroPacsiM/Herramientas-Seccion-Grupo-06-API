package com.llamatours.booking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreateBookingRequest {

    @NotNull
    private Long availabilityId;

    @NotNull
    @Positive
    private Integer peopleCount;
}
