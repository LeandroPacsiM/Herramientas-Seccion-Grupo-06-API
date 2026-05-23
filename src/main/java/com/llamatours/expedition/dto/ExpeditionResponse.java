package com.llamatours.expedition.dto;

import com.llamatours.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpeditionResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private Double price;
    private Integer durationDays;
    private Difficulty difficulty;
    private String location;
    private List<ItineraryDTO> itineraries;
    private List<ImageDTO> images;
    private List<AvailabilityDTO> availabilities;
}
