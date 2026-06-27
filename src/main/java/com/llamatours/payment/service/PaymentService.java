package com.llamatours.payment.service;

import com.llamatours.booking.entity.Booking;
import com.llamatours.booking.repository.BookingRepository;
import com.llamatours.booking.service.BookingService;
import com.llamatours.enums.BookingStatus;
import com.llamatours.expedition.entity.Expedition;
import com.llamatours.payment.dto.CreateCheckoutSessionResponse;
import com.llamatours.payment.dto.PaymentDetailsResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final BookingRepository bookingRepository;
    private final BookingService bookingService;

    @Value("${stripe.success.url}")
    private String successUrl;

    @Value("${stripe.cancel.url}")
    private String cancelUrl;

    public CreateCheckoutSessionResponse createCheckoutSession(Long bookingId, Long userId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Booking does not belong to user");
        }

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in PENDING status");
        }

        Expedition expedition = booking.getExpedition();

        String displayReceipt = "LLT-"
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))
                + "-" + String.format("%04d", bookingId);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(successUrl + "?bookingId=" + bookingId)
                .setCancelUrl(cancelUrl + "/" + bookingId)
                .setClientReferenceId(bookingId.toString())
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .setDescription("Booking " + displayReceipt)
                                .putMetadata("bookingId", bookingId.toString())
                                .build()
                )
                .putMetadata("bookingId", bookingId.toString())
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (booking.getTotalAmount() * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(expedition.getName())
                                                                .putMetadata("expeditionId", expedition.getId().toString())
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .build();

        try {
            Session session = Session.create(params);
            return CreateCheckoutSessionResponse.builder()
                    .sessionUrl(session.getUrl())
                    .sessionId(session.getId())
                    .build();
        } catch (StripeException e) {
            log.error("Stripe error creating checkout session for booking {}: {}", bookingId, e.getMessage());
            throw new RuntimeException("Failed to create payment session: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public PaymentDetailsResponse getPaymentDetails(Long bookingId, Long userId) {
        var booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        if (!booking.getUser().getId().equals(userId)) {
            throw new RuntimeException("Booking does not belong to user");
        }

        var user = booking.getUser();
        var expedition = booking.getExpedition();
        var availability = booking.getAvailability();

        return PaymentDetailsResponse.builder()
                .bookingId(booking.getId())
                .receiptNumber(booking.getReceiptNumber())
                .expeditionName(expedition.getName())
                .startDate(availability.getStartDate().toString())
                .endDate(availability.getEndDate().toString())
                .peopleCount(booking.getPeopleCount())
                .totalAmount(booking.getTotalAmount())
                .status(booking.getStatus().name())
                .paymentId(booking.getPaymentId())
                .bookingDate(booking.getCreatedAt() != null ? booking.getCreatedAt().toString() : null)
                .customerName(user.getName())
                .customerEmail(user.getEmail())
                .build();
    }

    @Transactional
    public void handleCheckoutCompleted(String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            String bookingIdStr = session.getMetadata().get("bookingId");

            if (bookingIdStr == null) {
                log.error("No bookingId in session metadata for session {}", sessionId);
                return;
            }

            Long bookingId = Long.parseLong(bookingIdStr);
            bookingService.confirmBooking(bookingId, sessionId);
            log.info("Booking {} confirmed after successful payment session {}", bookingId, sessionId);
        } catch (StripeException e) {
            log.error("Error retrieving session {}: {}", sessionId, e.getMessage());
            throw new RuntimeException("Failed to process webhook: " + e.getMessage());
        }
    }
}
