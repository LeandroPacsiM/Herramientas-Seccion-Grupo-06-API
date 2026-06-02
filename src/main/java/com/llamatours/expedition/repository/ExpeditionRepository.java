package com.llamatours.expedition.repository;

import com.llamatours.expedition.entity.Expedition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpeditionRepository extends JpaRepository<Expedition, Long> {

    Optional<Expedition> findBySlug(String slug);

    boolean existsBySlug(String slug);
}
