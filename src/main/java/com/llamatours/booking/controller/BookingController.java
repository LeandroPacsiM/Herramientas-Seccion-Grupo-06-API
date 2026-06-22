package com.llamatours.booking.controller;

import com.llamatours.booking.dto.BookingResponse;
import com.llamatours.booking.dto.CreateBookingRequest;
import com.llamatours.booking.service.BookingService;
import com.llamatours.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody CreateBookingRequest request,
                                                          @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookingService.createBooking(request, user));
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getUserBookings(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookingService.getUserBookings(user.getId()));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id, @AuthenticationPrincipal User user) {
        bookingService.cancelBooking(id, user.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<BookingResponse> payBooking(@PathVariable Long id,
                                                       @RequestParam String paymentId,
                                                       @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(bookingService.payBooking(id, paymentId, user.getId()));
    }
}

