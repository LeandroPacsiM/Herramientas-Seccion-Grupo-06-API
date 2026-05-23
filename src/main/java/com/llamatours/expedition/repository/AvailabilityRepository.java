package com.llamatours.expedition.repository;

import com.llamatours.expedition.entity.Availability;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
}
