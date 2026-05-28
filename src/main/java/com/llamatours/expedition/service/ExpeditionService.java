package com.llamatours.expedition.service;

import com.llamatours.expedition.dto.CreateExpeditionRequest;
import com.llamatours.expedition.dto.ExpeditionResponse;
import com.llamatours.expedition.entity.Availability;
import com.llamatours.expedition.entity.Image;
import com.llamatours.expedition.entity.Itinerary;
import com.llamatours.expedition.mapper.ExpeditionMapper;
import com.llamatours.expedition.repository.ExpeditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExpeditionService {

    private final ExpeditionRepository expeditionRepository;
    private final ExpeditionMapper expeditionMapper;

    public List<ExpeditionResponse> getAllExpeditions() {
        return expeditionMapper.toResponseList(expeditionRepository.findAll());
    }

    public ExpeditionResponse getExpeditionById(Long id) {
        var expedition = expeditionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expedition not found with id: " + id));
        return expeditionMapper.toResponse(expedition);
    }

    @Transactional
    public ExpeditionResponse createExpedition(CreateExpeditionRequest request) {
        if (expeditionRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Expedition with slug '" + request.getSlug() + "' already exists");
        }

        var expedition = expeditionMapper.toEntity(request);
        expedition = expeditionRepository.save(expedition);
        return expeditionMapper.toResponse(expedition);
    }

    @Transactional
    public ExpeditionResponse updateExpedition(Long id, CreateExpeditionRequest request) {
        var expedition = expeditionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expedition not found with id: " + id));

        if (!expedition.getSlug().equals(request.getSlug())
                && expeditionRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Expedition with slug '" + request.getSlug() + "' already exists");
        }

        expedition.setName(request.getName());
        expedition.setSlug(request.getSlug());
        expedition.setDescription(request.getDescription());
        expedition.setPrice(request.getPrice());
        expedition.setDurationDays(request.getDurationDays());
        expedition.setDifficulty(request.getDifficulty());
        expedition.setLocation(request.getLocation());

        if (request.getItineraries() != null) {
            expedition.getItineraries().clear();
            request.getItineraries().forEach(dto -> {
                var item = new Itinerary();
                item.setDayNumber(dto.getDayNumber());
                item.setTitle(dto.getTitle());
                item.setDescription(dto.getDescription());
                item.setExpedition(expedition);
                expedition.getItineraries().add(item);
            });
        }

        if (request.getImages() != null) {
            expedition.getImages().clear();
            request.getImages().forEach(dto -> {
                var image = new Image();
                image.setUrl(dto.getUrl());
                image.setImageOrder(dto.getImageOrder());
                image.setExpedition(expedition);
                expedition.getImages().add(image);
            });
        }

        if (request.getAvailabilities() != null) {
            expedition.getAvailabilities().clear();
            request.getAvailabilities().forEach(dto -> {
                var availability = new Availability();
                availability.setStartDate(dto.getStartDate());
                availability.setEndDate(dto.getEndDate());
                availability.setCapacity(dto.getCapacity());
                availability.setAvailableSpots(dto.getCapacity());
                availability.setExpedition(expedition);
                expedition.getAvailabilities().add(availability);
            });
        }

        expeditionRepository.save(expedition);
        return expeditionMapper.toResponse(expedition);
    }

    @Transactional
    public void deleteExpedition(Long id) {
        if (!expeditionRepository.existsById(id)) {
            throw new RuntimeException("Expedition not found with id: " + id);
        }
        expeditionRepository.deleteById(id);
    }
}
