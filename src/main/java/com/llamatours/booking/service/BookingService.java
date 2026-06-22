package com.llamatours.booking.service;

import com.llamatours.booking.dto.BookingResponse;
import com.llamatours.booking.dto.CreateBookingRequest;
import com.llamatours.booking.entity.Booking;
import com.llamatours.booking.repository.BookingRepository;
import com.llamatours.enums.BookingStatus;
import com.llamatours.expedition.repository.AvailabilityRepository;
import com.llamatours.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;
    private final AvailabilityRepository availabilityRepository;

    @Transactional
    public BookingResponse createBooking(CreateBookingRequest request, User user) {
        var availability = availabilityRepository.findById(request.getAvailabilityId())
                .orElseThrow(() -> new RuntimeException("Availability not found with id: " + request.getAvailabilityId()));

        if (availability.getAvailableSpots() < request.getPeopleCount()) {
            throw new RuntimeException("Not enough available spots");
        }

        if (availability.getStartDate().isBefore(java.time.LocalDate.now())) {
            throw new RuntimeException("Cannot book a past date");
        }

        var expedition = availability.getExpedition();
        var now = LocalDateTime.now();
        var totalAmount = request.getPeopleCount() * expedition.getPrice();

        var booking = Booking.builder()
                .peopleCount(request.getPeopleCount())
                .status(BookingStatus.PENDING)
                .totalAmount(totalAmount)
                .createdAt(now)
                .updatedAt(now)
                .user(user)
                .expedition(expedition)
                .availability(availability)
                .build();

        availability.setAvailableSpots(availability.getAvailableSpots() - request.getPeopleCount());

        booking = bookingRepository.save(booking);
        return toResponse(booking);
    }

    public List<BookingResponse> getUserBookings(Long userId) {
        return bookingRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void cancelBooking(Long bookingId, Long userId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Booking does not belong to user");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        booking.setUpdatedAt(LocalDateTime.now());
        booking.getAvailability().setAvailableSpots(
                booking.getAvailability().getAvailableSpots() + booking.getPeopleCount()
        );
    }

    @Transactional
    public BookingResponse payBooking(Long bookingId, String paymentId, Long userId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Booking does not belong to user");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in PENDING status");
        }

        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setPaymentId(paymentId);
        booking.setUpdatedAt(LocalDateTime.now());

        return toResponse(booking);
    }

    private BookingResponse toResponse(Booking booking) {
        return BookingResponse.builder()
                .id(booking.getId())
                .peopleCount(booking.getPeopleCount())
                .status(booking.getStatus())
                .userId(booking.getUser().getId())
                .expeditionId(booking.getExpedition().getId())
                .expeditionName(booking.getExpedition().getName())
                .availabilityId(booking.getAvailability().getId())
                .startDate(booking.getAvailability().getStartDate())
                .endDate(booking.getAvailability().getEndDate())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .totalAmount(booking.getTotalAmount())
                .paymentId(booking.getPaymentId())
                .build();
    }
}

