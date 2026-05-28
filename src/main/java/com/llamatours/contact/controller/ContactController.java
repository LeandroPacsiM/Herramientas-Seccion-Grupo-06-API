package com.llamatours.contact.controller;

import com.llamatours.contact.dto.CreateContactRequest;
import com.llamatours.contact.service.ContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<Void> createContact(@Valid @RequestBody CreateContactRequest request) {
        contactService.createContact(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
