package com.llamatours.expedition.controller;

import com.llamatours.expedition.dto.CreateExpeditionRequest;
import com.llamatours.expedition.dto.ExpeditionResponse;
import com.llamatours.expedition.service.ExpeditionService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/expeditions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminExpeditionController {

    private final ExpeditionService expeditionService;

    @GetMapping
    public ResponseEntity<List<ExpeditionResponse>> getAllExpeditions() {
        return ResponseEntity.ok(expeditionService.getAllExpeditions());
    }

    @PostMapping
    public ResponseEntity<ExpeditionResponse> createExpedition(@Valid @RequestBody CreateExpeditionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expeditionService.createExpedition(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpeditionResponse> updateExpedition(@PathVariable Long id,
                                                                @Valid @RequestBody CreateExpeditionRequest request) {
        return ResponseEntity.ok(expeditionService.updateExpedition(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpedition(@PathVariable Long id) {
        expeditionService.deleteExpedition(id);
        return ResponseEntity.noContent().build();
    }
}
