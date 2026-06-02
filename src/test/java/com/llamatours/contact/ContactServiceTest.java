package com.llamatours.contact;

import com.llamatours.contact.dto.CreateContactRequest;
import com.llamatours.contact.service.ContactService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    @Test
    void createContact_shouldWorkWithoutExpedition() {
        var request = new CreateContactRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setMessage("I have a question about tours");

        assertDoesNotThrow(() -> contactService.createContact(request));
    }

    @Test
    void createContact_shouldThrowWhenExpeditionNotFound() {
        var request = new CreateContactRequest();
        request.setName("Jane Doe");
        request.setEmail("jane@example.com");
        request.setMessage("Question about expedition");
        request.setExpeditionId(99999L);

        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> contactService.createContact(request)
        );
    }
}
