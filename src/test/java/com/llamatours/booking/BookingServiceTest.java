package com.llamatours.booking;

import com.llamatours.booking.dto.CreateBookingRequest;
import com.llamatours.booking.service.BookingService;
import com.llamatours.enums.BookingStatus;
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
        availabilityDto.setStartDate(LocalDate.now().plusDays(10));
        availabilityDto.setEndDate(LocalDate.now().plusDays(15));
        availabilityDto.setCapacity(10);

        expeditionReq.setAvailabilities(List.of(availabilityDto));

        var expedition = expeditionService.createExpedition(expeditionReq);
        availabilityId = expedition.getAvailabilities().get(0).getId();
    }

    @Test
    void createBooking_shouldReduceAvailableSpotsAndSetPending() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(2);

        var response = bookingService.createBooking(request, testUser);

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(2, response.getPeopleCount());
        assertEquals(testUser.getId(), response.getUserId());
        assertEquals(BookingStatus.PENDING, response.getStatus());
        assertEquals(200.0, response.getTotalAmount());
        assertNotNull(response.getCreatedAt());
        assertNotNull(response.getUpdatedAt());
        assertNull(response.getPaymentId());
    }

    @Test
    void createBooking_shouldThrowWhenNotEnoughSpots() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(100);

        assertThrows(RuntimeException.class, () -> bookingService.createBooking(request, testUser));
    }

    @Test
    void createBooking_shouldThrowWhenDateIsPast() {
        var slug = "past-expedition-" + UUID.randomUUID();

        var expeditionReq = new CreateExpeditionRequest();
        expeditionReq.setName("Past Expedition");
        expeditionReq.setSlug(slug);
        expeditionReq.setDescription("test");
        expeditionReq.setPrice(100.0);
        expeditionReq.setDurationDays(3);
        expeditionReq.setDifficulty(Difficulty.EASY);
        expeditionReq.setLocation("Test");

        var availabilityDto = new AvailabilityDTO();
        availabilityDto.setStartDate(LocalDate.now().minusDays(5));
        availabilityDto.setEndDate(LocalDate.now().minusDays(1));
        availabilityDto.setCapacity(10);

        expeditionReq.setAvailabilities(List.of(availabilityDto));

        var expedition = expeditionService.createExpedition(expeditionReq);
        var pastAvailabilityId = expedition.getAvailabilities().get(0).getId();

        var request = new CreateBookingRequest();
        request.setAvailabilityId(pastAvailabilityId);
        request.setPeopleCount(2);

        var exception = assertThrows(RuntimeException.class, () -> 
                bookingService.createBooking(request, testUser)
        );
        assertEquals("Cannot book a past date", exception.getMessage());
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

    @Test
    void payBooking_shouldChangeStatusToConfirmedAndRecordPaymentId() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(2);

        var created = bookingService.createBooking(request, testUser);
        assertEquals(BookingStatus.PENDING, created.getStatus());
        assertNull(created.getPaymentId());

        var paid = bookingService.payBooking(created.getId(), "PAY-12345", testUser.getId());
        assertEquals(BookingStatus.CONFIRMED, paid.getStatus());
        assertEquals("PAY-12345", paid.getPaymentId());
        assertNotNull(paid.getUpdatedAt());
    }

    @Test
    void payBooking_shouldThrowIfAlreadyConfirmed() {
        var request = new CreateBookingRequest();
        request.setAvailabilityId(availabilityId);
        request.setPeopleCount(2);

        var created = bookingService.createBooking(request, testUser);
        bookingService.payBooking(created.getId(), "PAY-12345", testUser.getId());

        assertThrows(RuntimeException.class, () -> 
                bookingService.payBooking(created.getId(), "PAY-67890", testUser.getId())
        );
    }
}


