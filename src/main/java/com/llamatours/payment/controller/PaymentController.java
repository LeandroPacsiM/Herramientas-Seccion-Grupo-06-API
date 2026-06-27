package com.llamatours.payment.controller;

import com.llamatours.payment.dto.CreateCheckoutSessionResponse;
import com.llamatours.payment.dto.PaymentDetailsResponse;
import com.llamatours.payment.service.PaymentService;
import com.llamatours.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create-checkout-session")
    public ResponseEntity<CreateCheckoutSessionResponse> createCheckoutSession(
            @RequestParam Long bookingId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.createCheckoutSession(bookingId, user.getId()));
    }

    @GetMapping("/{bookingId}/details")
    public ResponseEntity<PaymentDetailsResponse> getPaymentDetails(
            @PathVariable Long bookingId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(paymentService.getPaymentDetails(bookingId, user.getId()));
    }
}
