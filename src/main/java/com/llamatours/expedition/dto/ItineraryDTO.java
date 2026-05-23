package com.llamatours.expedition.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItineraryDTO {

    private Long id;
    private Integer dayNumber;
    private String title;
    private String description;
}
