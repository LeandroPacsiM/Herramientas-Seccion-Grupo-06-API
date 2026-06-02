package com.llamatours.expedition.entity;

import com.llamatours.enums.Difficulty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expeditions")
public class Expedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(nullable = false)
    private String location;

    @OneToMany(mappedBy = "expedition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Itinerary> itineraries = new ArrayList<>();

    @OneToMany(mappedBy = "expedition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "expedition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Availability> availabilities = new ArrayList<>();
}
