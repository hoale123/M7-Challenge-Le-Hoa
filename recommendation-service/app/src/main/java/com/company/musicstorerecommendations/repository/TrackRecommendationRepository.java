package com.company.musicstorerecommendations.repository;

import com.company.musicstorerecommendations.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Integer> {
}
