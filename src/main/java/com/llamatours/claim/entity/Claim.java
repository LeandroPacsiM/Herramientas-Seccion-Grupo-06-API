package com.llamatours.claim.entity;

import com.llamatours.claim.enums.ClaimStatus;
import com.llamatours.claim.enums.ClaimType;
import com.llamatours.claim.enums.DocumentType;
import com.llamatours.claim.enums.GoodType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "claim_type", nullable = false)
    private ClaimType claimType;

    // Consumidor Reclamante
    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private boolean minor;

    @Column(name = "guardian_name")
    private String guardianName;

    @Enumerated(EnumType.STRING)
    @Column(name = "guardian_document_type")
    private DocumentType guardianDocumentType;

    @Column(name = "guardian_document_number")
    private String guardianDocumentNumber;

    // Identificación del Bien Contratado
    @Enumerated(EnumType.STRING)
    @Column(name = "good_type", nullable = false)
    private GoodType goodType;

    @Column(name = "claimed_amount")
    private BigDecimal claimedAmount;

    @Column(name = "good_description", columnDefinition = "TEXT", nullable = false)
    private String goodDescription;

    // Detalle de la Reclamación y Pedido
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Column(name = "consumer_request", columnDefinition = "TEXT", nullable = false)
    private String consumerRequest;

    // Respuesta del Proveedor (Admin)
    @Column(columnDefinition = "TEXT")
    private String reply;

    @Column(name = "replied_at")
    private LocalDateTime repliedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.status == null) {
            this.status = ClaimStatus.PENDIENTE;
        }
    }
}
