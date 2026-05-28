package com.llamatours.expedition.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(name = "image_order")
    private Integer imageOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "expedition_id", nullable = false)
    @JsonIgnore
    private Expedition expedition;
}
