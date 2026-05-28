package com.llamatours.expedition.dto;

import com.llamatours.enums.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class CreateExpeditionRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    private String description;

    @NotNull
    @Positive
    private Double price;

    @NotNull
    @Positive
    private Integer durationDays;

    @NotNull
    private Difficulty difficulty;

    @NotBlank
    private String location;

    private List<ItineraryDTO> itineraries;

    private List<ImageDTO> images;

    private List<AvailabilityDTO> availabilities;
}
