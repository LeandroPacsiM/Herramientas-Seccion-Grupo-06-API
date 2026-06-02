package com.llamatours.expedition.controller;

import com.llamatours.expedition.dto.ExpeditionResponse;
import com.llamatours.expedition.service.ExpeditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/expeditions")
@RequiredArgsConstructor
public class ExpeditionController {

    private final ExpeditionService expeditionService;

    @GetMapping
    public ResponseEntity<List<ExpeditionResponse>> getAllExpeditions() {
        return ResponseEntity.ok(expeditionService.getAllExpeditions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpeditionResponse> getExpeditionById(@PathVariable Long id) {
        return ResponseEntity.ok(expeditionService.getExpeditionById(id));
    }
}
