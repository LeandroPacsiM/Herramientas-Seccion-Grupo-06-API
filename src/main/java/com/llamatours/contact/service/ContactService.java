package com.llamatours.contact.service;

import com.llamatours.contact.dto.CreateContactRequest;
import com.llamatours.contact.entity.Contact;
import com.llamatours.contact.repository.ContactRepository;
import com.llamatours.expedition.repository.ExpeditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final ExpeditionRepository expeditionRepository;

    @Transactional
    public void createContact(CreateContactRequest request) {
        var contact = Contact.builder()
                .name(request.getName())
                .email(request.getEmail())
                .message(request.getMessage())
                .build();

        if (request.getExpeditionId() != null) {
            var expedition = expeditionRepository.findById(request.getExpeditionId())
                    .orElseThrow(() -> new RuntimeException("Expedition not found with id: " + request.getExpeditionId()));
            contact.setExpedition(expedition);
        }

        contactRepository.save(contact);
    }
}
