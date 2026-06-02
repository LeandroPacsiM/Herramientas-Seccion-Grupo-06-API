package com.llamatours.expedition.mapper;

import com.llamatours.expedition.dto.*;
import com.llamatours.expedition.entity.Availability;
import com.llamatours.expedition.entity.Expedition;
import com.llamatours.expedition.entity.Image;
import com.llamatours.expedition.entity.Itinerary;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class ExpeditionMapper {

    public ExpeditionResponse toResponse(Expedition expedition) {
        return ExpeditionResponse.builder()
                .id(expedition.getId())
                .name(expedition.getName())
                .slug(expedition.getSlug())
                .description(expedition.getDescription())
                .price(expedition.getPrice())
                .durationDays(expedition.getDurationDays())
                .difficulty(expedition.getDifficulty())
                .location(expedition.getLocation())
                .itineraries(mapItineraries(expedition.getItineraries()))
                .images(mapImages(expedition.getImages()))
                .availabilities(mapAvailabilities(expedition.getAvailabilities()))
                .build();
    }

    public List<ExpeditionResponse> toResponseList(List<Expedition> expeditions) {
        return expeditions.stream().map(this::toResponse).toList();
    }

    public Expedition toEntity(CreateExpeditionRequest request) {
        Expedition expedition = Expedition.builder()
                .name(request.getName())
                .slug(request.getSlug())
                .description(request.getDescription())
                .price(request.getPrice())
                .durationDays(request.getDurationDays())
                .difficulty(request.getDifficulty())
                .location(request.getLocation())
                .build();

        if (request.getItineraries() != null) {
            expedition.setItineraries(request.getItineraries().stream()
                    .map(dto -> {
                        Itinerary item = new Itinerary();
                        item.setDayNumber(dto.getDayNumber());
                        item.setTitle(dto.getTitle());
                        item.setDescription(dto.getDescription());
                        item.setExpedition(expedition);
                        return item;
                    })
                    .toList());
        }

        if (request.getImages() != null) {
            expedition.setImages(request.getImages().stream()
                    .map(dto -> {
                        Image image = new Image();
                        image.setUrl(dto.getUrl());
                        image.setImageOrder(dto.getImageOrder());
                        image.setExpedition(expedition);
                        return image;
                    })
                    .toList());
        }

        if (request.getAvailabilities() != null) {
            expedition.setAvailabilities(request.getAvailabilities().stream()
                    .map(dto -> {
                        Availability availability = new Availability();
                        availability.setStartDate(dto.getStartDate());
                        availability.setEndDate(dto.getEndDate());
                        availability.setCapacity(dto.getCapacity());
                        availability.setAvailableSpots(dto.getCapacity());
                        availability.setExpedition(expedition);
                        return availability;
                    })
                    .toList());
        }

        return expedition;
    }

    private List<ItineraryDTO> mapItineraries(List<Itinerary> itineraries) {
        if (itineraries == null) return Collections.emptyList();
        return itineraries.stream()
                .map(i -> ItineraryDTO.builder()
                        .id(i.getId())
                        .dayNumber(i.getDayNumber())
                        .title(i.getTitle())
                        .description(i.getDescription())
                        .build())
                .toList();
    }

    private List<ImageDTO> mapImages(List<Image> images) {
        if (images == null) return Collections.emptyList();
        return images.stream()
                .map(i -> ImageDTO.builder()
                        .id(i.getId())
                        .url(i.getUrl())
                        .imageOrder(i.getImageOrder())
                        .build())
                .toList();
    }

    private List<AvailabilityDTO> mapAvailabilities(List<Availability> availabilities) {
        if (availabilities == null) return Collections.emptyList();
        return availabilities.stream()
                .map(a -> AvailabilityDTO.builder()
                        .id(a.getId())
                        .startDate(a.getStartDate())
                        .endDate(a.getEndDate())
                        .capacity(a.getCapacity())
                        .availableSpots(a.getAvailableSpots())
                        .build())
                .toList();
    }
}
