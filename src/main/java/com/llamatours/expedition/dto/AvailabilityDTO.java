package com.llamatours.expedition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private Integer availableSpots;
}
