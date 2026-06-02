package com.llamatours.expedition;

import com.llamatours.enums.Difficulty;
import com.llamatours.expedition.dto.CreateExpeditionRequest;
import com.llamatours.expedition.service.ExpeditionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExpeditionServiceTest {

    @Autowired
    private ExpeditionService expeditionService;

    @Test
    void createAndGetExpedition_shouldWork() {
        var request = new CreateExpeditionRequest();
        request.setName("Test Expedition");
        request.setSlug("test-expedition");
        request.setDescription("A test expedition");
        request.setPrice(999.99);
        request.setDurationDays(5);
        request.setDifficulty(Difficulty.MODERATE);
        request.setLocation("Test Location");

        var created = expeditionService.createExpedition(request);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals("Test Expedition", created.getName());

        var fetched = expeditionService.getExpeditionById(created.getId());
        assertEquals("Test Expedition", fetched.getName());
        assertEquals("test-expedition", fetched.getSlug());
    }

    @Test
    void create_shouldThrowWhenSlugExists() {
        var request = new CreateExpeditionRequest();
        request.setName("First");
        request.setSlug("duplicate-slug");
        request.setDescription("First");
        request.setPrice(100.0);
        request.setDurationDays(1);
        request.setDifficulty(Difficulty.EASY);
        request.setLocation("Loc");

        expeditionService.createExpedition(request);

        var duplicate = new CreateExpeditionRequest();
        duplicate.setName("Second");
        duplicate.setSlug("duplicate-slug");
        duplicate.setDescription("Second");
        duplicate.setPrice(200.0);
        duplicate.setDurationDays(2);
        duplicate.setDifficulty(Difficulty.HARD);
        duplicate.setLocation("Loc2");

        assertThrows(RuntimeException.class, () -> expeditionService.createExpedition(duplicate));
    }

    @Test
    void getAllExpeditions_shouldReturnList() {
        var request = new CreateExpeditionRequest();
        request.setName("List Test");
        request.setSlug("list-test");
        request.setDescription("test");
        request.setPrice(50.0);
        request.setDurationDays(1);
        request.setDifficulty(Difficulty.EASY);
        request.setLocation("Loc");

        expeditionService.createExpedition(request);

        var all = expeditionService.getAllExpeditions();
        assertFalse(all.isEmpty());
        assertTrue(all.stream().anyMatch(e -> e.getSlug().equals("list-test")));
    }

    @Test
    void deleteExpedition_shouldWork() {
        var request = new CreateExpeditionRequest();
        request.setName("Delete Me");
        request.setSlug("delete-me");
        request.setDescription("to be deleted");
        request.setPrice(10.0);
        request.setDurationDays(1);
        request.setDifficulty(Difficulty.EASY);
        request.setLocation("Loc");

        var created = expeditionService.createExpedition(request);

        expeditionService.deleteExpedition(created.getId());

        assertThrows(RuntimeException.class, () -> expeditionService.getExpeditionById(created.getId()));
    }
}
