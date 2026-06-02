package com.llamatours.booking;

import com.llamatours.booking.dto.CreateBookingRequest;
import com.llamatours.booking.service.BookingService;
import com.llamatours.enums.Difficulty;
import com.llamatours.enums.Role;
import com.llamatours.expedition.dto.CreateExpeditionRequest;
import com.llamatours.expedition.dto.AvailabilityDTO;
import com.llamatours.expedition.service.ExpeditionService;
import com.llamatours.user.entity.User;
import com.llamatours.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookingServiceTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ExpeditionService expeditionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private Long availabilityId;

    @BeforeEach
    void setUp() {
        var email = "booking-" + UUID.randomUUID() + "@example.com";

        testUser = userRepository.save(User.builder()
                .name("Booking User")
                .email(email)
                .password(passwordEncoder.encode("password"))
                .role(Role.USER)
                .build());

        var slug = "booking-expedition-" + UUID.randomUUID();

        var expeditionReq = new CreateExpeditionRequest();
        expeditionReq.setName("Booking Expedition");
        expeditionReq.setSlug(slug);
        expeditionReq.setDescription("test");
        expeditionReq.setPrice(100.0);
        expeditionReq.setDurationDays(3);
        expeditionReq.setDifficulty(Difficulty.EASY);
        expeditionReq.setLocation("Test");

        var availabilityDto = new AvailabilityDTO();
        availabilityDto.setStartDate(LocalDate.of(2026, 6, 1));
        availabilityDto.setEndDate(LocalDate.of(2026, 6, 5));
        availabilityDto.setCapacity(10);

        expeditionReq.setAvailabilities(List.of(availabilityDto));

        var expedition = expeditionService.createExpedition(expeditionReq);
        availabilityId = expedition.getAvailabilities().get(0).getId();
    }

    @Test
    void createBooking_shouldReduceAvailableSpots() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(2);

        var response = bookingService.createBooking(request, testUser);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(2, response.getPeopleCount());
        assertEquals(testUser.getId(), response.getUserId());
    }

    @Test
    void createBooking_shouldThrowWhenNotEnoughSpots() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(100);

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(request, testUser));
    }

    @Test
    void getUserBookings_shouldReturnUserBookings() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(1);
        bookingService.createBooking(request, testUser);

        var bookings = bookingService.getUserBookings(testUser.getId());
        assertEquals(1, bookings.size());
        assertEquals(testUser.getId(), bookings.get(0).getUserId());
    }
}
